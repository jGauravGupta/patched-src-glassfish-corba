/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 1997-2010 Oracle and/or its affiliates. All rights reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * 
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 * 
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 * 
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
/*
 * COMPONENT_NAME: idl.toJava
 *
 * ORIGINS: 27
 *
 * Licensed Materials - Property of IBM
 * 5639-D57 (C) COPYRIGHT International Business Machines Corp. 1997, 1999
 * RMI-IIOP v1.0
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.sun.tools.corba.se.idl.toJavaPortable;

// NOTES:
// -D61056   <klr> Use Util.helperName

import java.io.File;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.sun.tools.corba.se.idl.GenFileStream;
import com.sun.tools.corba.se.idl.EnumEntry;
import com.sun.tools.corba.se.idl.SymtabEntry;

/**
 *
 **/
public class EnumGen implements com.sun.tools.corba.se.idl.EnumGen, JavaGenerator
{
  /**
   * Public zero-argument constructor.
   **/
  public EnumGen ()
  {
  } // ctor

  /**
   * Generate the Java code for an IDL enumeration.
   **/
  public void generate (Hashtable symbolTable, EnumEntry e, PrintWriter s)
  {
    this.symbolTable = symbolTable;
    this.e           = e;
    init ();
    
    openStream ();
    if (stream == null) return;
    generateHolder ();
    generateHelper ();
    writeHeading ();
    writeBody ();
    writeClosing ();
    closeStream ();
  } // generate

  /**
   * Initialize members unique to this generator.
   **/
  protected void init ()
  {
    className = e.name ();
    fullClassName = Util.javaName (e);
  }

  /**
   * Open the print stream to which to write the enumeration class.
   **/
  protected void openStream ()
  {
    stream = Util.stream (e, ".java");
  }

  /**
   * Generate the holder class for this enumeration.
   **/
  protected void generateHolder ()
  {
    ((Factories)Compile.compiler.factories ()).holder ().generate (symbolTable, e);
  }

  /**
   * Generate the helper class for this enumeration.
   **/
  protected void generateHelper ()
  {
    ((Factories)Compile.compiler.factories ()).helper ().generate (symbolTable, e);
  }

  /**
   * Write the heading of the enumeration class, including the package,
   * imports, class statement, and open curly.
   **/
  protected void writeHeading ()
  {
    Util.writePackage (stream, e);
    Util.writeProlog (stream, ((GenFileStream)stream).name ());
    if (e.comment () != null)
      e.comment ().generate ("", stream);
    stream.println ("public class " + className + " implements org.omg.CORBA.portable.IDLEntity");
    stream.println ("{");
  }

  /**
   * Write the members of enumeration class.
   **/
  protected void writeBody ()
  {
    stream.println ("  private        int __value;");
    stream.println ("  private static int __size = " + (e.elements ().size ()) + ';');
    stream.println ("  private static " + fullClassName + "[] __array = new " + fullClassName + " [__size];");
    stream.println ();
    for (int i = 0; i < e.elements ().size (); ++i)
    {
      String label = (String)e.elements ().elementAt (i);
      stream.println ("  public static final int _" + label + " = " + i + ';');
      stream.println ("  public static final " + fullClassName + ' ' + label + " = new " + fullClassName + "(_" + label + ");");
    }
    stream.println ();
    writeValue ();
    writeFromInt ();
    writeCtors ();
  }

  /**
   * Write the value method for the enumeration class.
   **/
  protected void writeValue ()
  {
    stream.println ("  public int value ()");
    stream.println ("  {");
    stream.println ("    return __value;");
    stream.println ("  }");
    stream.println ();
  } // writeValue

  /**
   * Write the from_int method for the enumeration class.
   **/
  protected void writeFromInt ()
  {
    stream.println ("  public static " + fullClassName + " from_int (int value)");
    stream.println ("  {");
    stream.println ("    if (value >= 0 && value < __size)");
    stream.println ("      return __array[value];");
    stream.println ("    else");
    stream.println ("      throw new org.omg.CORBA.BAD_PARAM ();");
    stream.println ("  }");
    stream.println ();
  }

  /**
   * Write the protected constructor for the enumeration class.
   **/
  protected void writeCtors ()
  {
    stream.println ("  protected " + className + " (int value)");
    stream.println ("  {");
    stream.println ("    __value = value;");
    stream.println ("    __array[__value] = this;");
    stream.println ("  }");
  }

  /**
   * Close the enumeration class.
   **/
  protected void writeClosing ()
  {
    stream.println ("} // class " + className);
  }

  /**
   * Close the print stream, which writes the stream to file.
   **/
  protected void closeStream ()
  {
    stream.close ();
  }

  ///////////////
  // From JavaGenerator

  public int helperType (int index, String indent, TCOffsets tcoffsets, String name, SymtabEntry entry, PrintWriter stream)
  {
    tcoffsets.set (entry);
    EnumEntry enumEntry = (EnumEntry)entry;
    StringBuffer emit = new StringBuffer ("new String[] { ");
    Enumeration e = enumEntry.elements ().elements ();
    boolean firstTime = true;
    while (e.hasMoreElements ())
    {
      if (firstTime)
        firstTime = false;
      else
        emit.append (", ");
      emit.append ('"' + Util.stripLeadingUnderscores ((String)e.nextElement ()) + '"');
    }
    emit.append ("} ");
    stream.println (indent + name + " = org.omg.CORBA.ORB.init ().create_enum_tc ("
      + Util.helperName (enumEntry, true) + ".id (), \"" // <54697> // <d61056>
//      + "_id, \"" <54697>
      + Util.stripLeadingUnderscores (entry.name ()) + "\", "
      + new String (emit) + ");");
    return index + 1;

  } // helperType

  public int type (int index, String indent, TCOffsets tcoffsets, String name, SymtabEntry entry, PrintWriter stream) {
    stream.println (indent + name + " = " + Util.helperName (entry, true) + ".type ();"); // <d61056>
    return index;
  } // type

  public void helperRead (String entryName, SymtabEntry entry, PrintWriter stream)
  {
    stream.println ("    return " + Util.javaQualifiedName (entry) + ".from_int (istream.read_long ());");
  } // helperRead

  public void helperWrite (SymtabEntry entry, PrintWriter stream)
  {
    stream.println ("    ostream.write_long (value.value ());");
  } // helperWrite

  public int read (int index, String indent, String name, SymtabEntry entry, PrintWriter stream)
  {
    stream.println (indent + name + " = " + Util.javaQualifiedName (entry) + ".from_int (istream.read_long ());");
    return index;
  } // read

  public int write (int index, String indent, String name, SymtabEntry entry, PrintWriter stream)
  {
    stream.println (indent + "ostream.write_long (" + name + ".value ());");
    return index;
  } // write

  // From JavaGenerator
  ///////////////

  protected Hashtable    symbolTable = null;
  protected EnumEntry    e           = null;
  protected PrintWriter  stream      = null;

  // Member data unique to this generator
  String className     = null;
  String fullClassName = null;
} // class EnumGen


/*============================================================================
  DATE<AUTHOR>   ACTION
  ----------------------------------------------------------------------------
  31jul1997<daz> Modified to write comment immediately preceding class defining
                 enumeration declaration.
  12dec1998<klr> D55971 - omg 98-11-03 Java 2.4 RTF  - make subclassable
  ===========================================================================*/

