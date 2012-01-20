package com.biosimilarity.lift.lib.amqpJSONAPI2.Absyn; // Java Package generated by the BNF Converter.

public class KVDBReqURIHdr extends ReqHeader {
  public final URI uri_;

  public KVDBReqURIHdr(URI p1) { uri_ = p1; }

  public <R,A> R accept(com.biosimilarity.lift.lib.amqpJSONAPI2.Absyn.ReqHeader.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof com.biosimilarity.lift.lib.amqpJSONAPI2.Absyn.KVDBReqURIHdr) {
      com.biosimilarity.lift.lib.amqpJSONAPI2.Absyn.KVDBReqURIHdr x = (com.biosimilarity.lift.lib.amqpJSONAPI2.Absyn.KVDBReqURIHdr)o;
      return this.uri_.equals(x.uri_);
    }
    return false;
  }

  public int hashCode() {
    return this.uri_.hashCode();
  }


}