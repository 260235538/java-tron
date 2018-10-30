package org.tron.common.runtime.vm.cache;


import lombok.extern.slf4j.Slf4j;
import org.spongycastle.util.encoders.Hex;
import org.tron.common.utils.ByteArrayMap;
import org.tron.core.capsule.ProtoCapsule;
import org.tron.core.db.TronStoreWithRevoking;
import org.tron.core.exception.BadItemException;
import org.tron.core.exception.ItemNotFoundException;

@Slf4j(topic = "vm_read_write_cache")
public class ReadWriteCapsuleCache<V extends ProtoCapsule> implements CachedSource<byte[], V> {
  private ByteArrayMap<V> writeCache;
  private ByteArrayMap<V> readCache;
  private TronStoreWithRevoking<V> store;

  public ReadWriteCapsuleCache(TronStoreWithRevoking<V> store) {
    this.store = store;
    writeCache = new ByteArrayMap<>();
    readCache = new ByteArrayMap<>();
  }

  @Override
  public void put(byte[] key, V value) {
    writeCache.put(key, value);
  }

  @Override
  public V get(byte[] key) {
    if(writeCache.containsKey(key)) {
      return writeCache.get(key);
    }

    V value = readCache.get(key);
    V valueClone;
    if (value == null && !readCache.containsKey(key)){
      try {
        value = this.store.get(key);
        valueClone = this.store.of(value.getData());
      } catch (ItemNotFoundException | BadItemException e) {
        logger.warn("read cache null, key" + Hex.toHexString(key));
        value = null;
        valueClone = null;
      }
      // write into cache even though value is null, to prevent searching DB next time.
      // readCache just cached db data
      readCache.put(key, valueClone);
    }
    return value;
  }

  @Override
  public void commit() {
    writeCache.forEach((key, value) -> {
      if (logger.isDebugEnabled()){ logger.debug("commit cache, key" + Hex.toHexString(key) + " value:" + value); }
      if (value == null) {
        this.store.delete(key);
      } else {
        this.store.put(key, value);
      }
    });
  }
}
