package com.biosimilarity.lift.lib.amqpJSONAPI2.Absyn; // Java Package generated by the BNF Converter.

public abstract class TellReq implements java.io.Serializable {
  public abstract <R,A> R accept(TellReq.Visitor<R,A> v, A arg);
  public interface Visitor <R,A> {
    public R visit(com.biosimilarity.lift.lib.amqpJSONAPI2.Absyn.KVDBPutReq p, A arg);
    public R visit(com.biosimilarity.lift.lib.amqpJSONAPI2.Absyn.KVDBStoreReq p, A arg);
    public R visit(com.biosimilarity.lift.lib.amqpJSONAPI2.Absyn.KVDBPublishReq p, A arg);

  }

}