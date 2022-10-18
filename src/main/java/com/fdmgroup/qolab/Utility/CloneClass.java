package com.fdmgroup.qolab.Utility;

import com.fdmgroup.qolab.model.Trainee;

public class CloneClass implements Cloneable 
{ 
    int a, b; 
  
    Trainee c = new Trainee(); 
  
    public Object clone() throws
                CloneNotSupportedException 
    { 
        // Assign the shallow copy to new reference variable t 
        CloneClass t = (CloneClass)super.clone(); 
  
        t.c = new Trainee(); 
  
        // Create a new object for the field c 
        // and assign it to shallow copy obtained, 
        // to make it a deep copy 
        return t; 
    } 
} 