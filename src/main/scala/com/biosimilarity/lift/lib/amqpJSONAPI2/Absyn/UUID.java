package com.biosimilarity.lift.lib.amqpJSONAPI2.Absyn; // Java Package generated by the BNF Converter.

public abstract class UUID implements java.io.Serializable {
  public abstract <R,A> R accept(UUID.Visitor<R,A> v, A arg);
  public interface Visitor <R,A> {
    public R visit(com.biosimilarity.lift.lib.amqpJSONAPI2.Absyn.KVDBLUUID p, A arg);
    public R visit(com.biosimilarity.lift.lib.amqpJSONAPI2.Absyn.KVDBUUUID p, A arg);
    public R visit(com.biosimilarity.lift.lib.amqpJSONAPI2.Absyn.KVDBNullUUID p, A arg);

  }

}