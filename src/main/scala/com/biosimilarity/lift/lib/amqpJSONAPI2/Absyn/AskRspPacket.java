package com.biosimilarity.lift.lib.amqpJSONAPI2.Absyn; // Java Package generated by the BNF Converter.

public abstract class AskRspPacket implements java.io.Serializable {
  public abstract <R,A> R accept(AskRspPacket.Visitor<R,A> v, A arg);
  public interface Visitor <R,A> {
    public R visit(com.biosimilarity.lift.lib.amqpJSONAPI2.Absyn.KVDBAskRspData p, A arg);

  }

}