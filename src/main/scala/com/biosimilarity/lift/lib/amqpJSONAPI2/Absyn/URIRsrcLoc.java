package com.biosimilarity.lift.lib.amqpJSONAPI2.Absyn; // Java Package generated by the BNF Converter.

public class URIRsrcLoc extends URIRsrcLocation {
  public final NetLocation netlocation_;

  public URIRsrcLoc(NetLocation p1) { netlocation_ = p1; }

  public <R,A> R accept(com.biosimilarity.lift.lib.amqpJSONAPI2.Absyn.URIRsrcLocation.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof com.biosimilarity.lift.lib.amqpJSONAPI2.Absyn.URIRsrcLoc) {
      com.biosimilarity.lift.lib.amqpJSONAPI2.Absyn.URIRsrcLoc x = (com.biosimilarity.lift.lib.amqpJSONAPI2.Absyn.URIRsrcLoc)o;
      return this.netlocation_.equals(x.netlocation_);
    }
    return false;
  }

  public int hashCode() {
    return this.netlocation_.hashCode();
  }


}