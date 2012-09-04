package com.biosimilarity.lift.model.specialK.Absyn; // Java Package generated by the BNF Converter.

public class Literal extends Pattern {
  public final Value value_;

  public Literal(Value p1) { value_ = p1; }

  public <R,A> R accept(com.biosimilarity.lift.model.specialK.Absyn.Pattern.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof com.biosimilarity.lift.model.specialK.Absyn.Literal) {
      com.biosimilarity.lift.model.specialK.Absyn.Literal x = (com.biosimilarity.lift.model.specialK.Absyn.Literal)o;
      return this.value_.equals(x.value_);
    }
    return false;
  }

  public int hashCode() {
    return this.value_.hashCode();
  }


}