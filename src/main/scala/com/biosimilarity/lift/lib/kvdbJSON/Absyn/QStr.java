package com.biosimilarity.lift.lib.kvdbJSON.Absyn; // Java Package generated by the BNF Converter.

public class QStr extends QryGrndLit {
  public final String string_;

  public QStr(String p1) { string_ = p1; }

  public <R,A> R accept(com.biosimilarity.lift.lib.kvdbJSON.Absyn.QryGrndLit.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof com.biosimilarity.lift.lib.kvdbJSON.Absyn.QStr) {
      com.biosimilarity.lift.lib.kvdbJSON.Absyn.QStr x = (com.biosimilarity.lift.lib.kvdbJSON.Absyn.QStr)o;
      return this.string_.equals(x.string_);
    }
    return false;
  }

  public int hashCode() {
    return this.string_.hashCode();
  }


}