package org.tron.core.db;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PendingManager implements AutoCloseable {

  @Getter
  Manager dbManager;

  public PendingManager(Manager db) {
    this.dbManager = db;
    this.dbManager.getSuspensiveTransactions().addAll(db.getPendingTransactions());
    db.getPendingTransactions().clear();
    db.getDialog().reset();
  }

  @Override
  public void close() {
//    this.tmpTransactions.stream()
//        .filter(
//            trx -> dbManager.getTransactionStore().get(trx.getTransactionId().getBytes()) == null)
//        .forEach(trx -> {
//          try {
//            dbManager.pushTransactions(trx);
//          } catch (ValidateSignatureException e) {
//            logger.error(e.getMessage(), e);
//          } catch (ContractValidateException e) {
//            logger.error(e.getMessage(), e);
//          } catch (ContractExeException e) {
//            logger.error(e.getMessage(), e);
//          } catch (ValidateBandwidthException e) {
//            logger.error(e.getMessage(), e);
//          } catch (DupTransactionException e) {
//            logger.error("pending manager: dup trans", e);
//          } catch (TaposException e) {
//            logger.error("pending manager: tapos exception", e);
//          } catch (TooBigTransactionException e) {
//            logger.error("too big transaction");
//          } catch (TransactionExpirationException e) {
//            logger.error("expiration transaction");
//          }
//        });
//    dbManager.getSuspensiveTransactions().stream()
//        .filter(
//            trx -> dbManager.getTransactionStore().get(trx.getTransactionId().getBytes()) == null)
//        .forEach(trx -> {
//          try {
//            dbManager.pushTransactions(trx);
//          } catch (ValidateSignatureException e) {
//            logger.debug(e.getMessage(), e);
//          } catch (ContractValidateException e) {
//            logger.debug(e.getMessage(), e);
//          } catch (ContractExeException e) {
//            logger.debug(e.getMessage(), e);
//          } catch (ValidateBandwidthException e) {
//            logger.debug(e.getMessage(), e);
//          } catch (DupTransactionException e) {
//            logger.debug("pending manager: dup trans", e);
//          } catch (TaposException e) {
//            logger.debug("pending manager: tapos exception", e);
//          } catch (TooBigTransactionException e) {
//              logger.error("too big transaction");
//          } catch (TransactionExpirationException e) {
//              logger.error("expiration transaction");
//          }
//        });
//    dbManager.getSuspensiveTransactions().clear();
//    tmpTransactions.clear();
  }
}
