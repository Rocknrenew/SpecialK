package com.biosimilarity.lift.lib.amqpJSONAPI2.Absyn; // Java Package generated by the BNF Converter.

public class QAtomic extends QryValue {
  public final QryGrndLit qrygrndlit_;

  public QAtomic(QryGrndLit p1) { qrygrndlit_ = p1; }

  public <R,A> R accept(com.biosimilarity.lift.lib.amqpJSONAPI2.Absyn.QryValue.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof com.biosimilarity.lift.lib.amqpJSONAPI2.Absyn.QAtomic) {
      com.biosimilarity.lift.lib.amqpJSONAPI2.Absyn.QAtomic x = (com.biosimilarity.lift.lib.amqpJSONAPI2.Absyn.QAtomic)o;
      return this.qrygrndlit_.equals(x.qrygrndlit_);
    }
    return false;
  }

  public int hashCode() {
    return this.qrygrndlit_.hashCode();
  }


}