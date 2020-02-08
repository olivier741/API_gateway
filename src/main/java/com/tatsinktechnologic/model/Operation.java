/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.model;

import java.util.List;

/**
 *
 * @author olivier.tatsinkou
 */
public class Operation {
  private String name;
  private List parameterNames;
  
  public Operation(String name) {
      super();
      this.name = name;

  }

  public Operation(String name, List parameterNames) {
      super();
      this.name = name;
      this.parameterNames = parameterNames;
  }



  public String getName() {
      return name;
  }



  public void setName(String name) {
      this.name = name;
  }



  public List getParameterNames() {
      return parameterNames;
  }



  public void setParameterNames(List parameterNames) {
      this.parameterNames = parameterNames;
  }



  @Override

  public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((name == null) ? 0 : name.hashCode());
      return result;
  }



  @Override

  public boolean equals(Object obj) {
      if (this == obj)
          return true;
      if (obj == null)
          return false;
      if (getClass() != obj.getClass())
          return false;
      Operation other = (Operation) obj;
      if (name == null) {
          if (other.name != null)
              return false;
      } else if (!name.equals(other.name))
          return false;
      return true;
  }

    @Override
    public String toString() {
        return "Operation{" + "name=" + name + ", parameterNames=" + parameterNames + '}';
    }
  
  
}
