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

package com.sun.corba.ee.spi.logging ;

import org.glassfish.pfl.basic.logex.Chain;
import org.glassfish.pfl.basic.logex.ExceptionWrapper;
import org.glassfish.pfl.basic.logex.Log;
import org.glassfish.pfl.basic.logex.LogLevel;
import org.glassfish.pfl.basic.logex.Message;
import org.glassfish.pfl.basic.logex.WrapperGenerator;
import com.sun.corba.ee.spi.logex.corba.CS;
import com.sun.corba.ee.spi.logex.corba.CSValue;

import com.sun.corba.ee.spi.logex.corba.ORBException ;
import com.sun.corba.ee.spi.logex.corba.CorbaExtension ;
import java.io.IOException;

import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.DATA_CONVERSION;
import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.INV_OBJREF;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.UNKNOWN;

@ExceptionWrapper( idPrefix="IOP" )
@ORBException( omgException=false, group=CorbaExtension.UtilGroup )
public interface UtilSystemException {
    UtilSystemException self = WrapperGenerator.makeWrapper( 
        UtilSystemException.class, CorbaExtension.self ) ;
    
    @Log( level=LogLevel.FINE, id=1 )
    @Message( "StubFactory failed on makeStub call" )
    BAD_OPERATION stubFactoryCouldNotMakeStub( @Chain Throwable exc ) ;
    
    @Log( level=LogLevel.FINE, id=2 )
    @Message( "Error in making stub given RepositoryId" )
    BAD_OPERATION errorInMakeStubFromRepositoryId( @Chain Throwable thr ) ;
    
    @Log( level=LogLevel.WARNING, id=3 )
    @Message( "Failure in making stub given RepositoryId" )
    BAD_OPERATION failureInMakeStubFromRepositoryId( @Chain Throwable thr ) ;
    
    @Log( level=LogLevel.FINE, id=4 )
    @Message( "ClassCastException in loadStub" )
    BAD_OPERATION classCastExceptionInLoadStub( @Chain Exception exc ) ;
    
    @Log( level=LogLevel.WARNING, id=5 )
    @Message( "Exception in loadStub" )
    BAD_OPERATION exceptionInLoadStub( @Chain Exception exc ) ;
    
    @Log( level=LogLevel.WARNING, id=6 )
    @Message( "Unable to make stub from any of the repository IDs of the "
        + "interface" )
    BAD_OPERATION couldNotMakeStubFromRepositoryId(  ) ;
    
    @Log( level=LogLevel.WARNING, id=7 )
    @Message( "An IOException occurred while creating an IIOPOutputStream" )
    BAD_OPERATION exceptionInCreateIiopOutputStream( @Chain IOException exc ) ;
    
    @Log( level=LogLevel.WARNING, id=8 )
    @Message( "An IOException occurred while creating an IIOPInputStream" )
    BAD_OPERATION exceptionInCreateIiopInputStream( @Chain IOException exc ) ;
    
    @Log( level=LogLevel.WARNING, id=9 )
    @Message( "Only one call to the Util constructor is allowed; "
        + "normally Util.getInstance should be called" )
    BAD_OPERATION onlyOneCallToConstructorAllowed(  ) ;
    
    @Log( level=LogLevel.WARNING, id=2 )
    @Message( "Error in loadStubAndUpdateCache caused by _this_object" )
    BAD_PARAM noPoa( @Chain Exception exc ) ;
    
    @Log( level=LogLevel.FINE, id=3 )
    @Message( "Tried to connect already connected Stub Delegate to a "
        + "different ORB" )
    BAD_PARAM connectWrongOrb(  ) ;
    
    @Log( level=LogLevel.WARNING, id=4 )
    @Message( "Tried to connect unconnected Stub Delegate but no Tie was found" )
    BAD_PARAM connectNoTie(  ) ;
    
    @Log( level=LogLevel.WARNING, id=5 )
    @Message( "Tried to connect unconnected stub with Tie in a different ORB" )
    BAD_PARAM connectTieWrongOrb(  ) ;
    
    @Log( level=LogLevel.WARNING, id=6 )
    @Message( "Tried to connect unconnected stub to unconnected Tie" )
    BAD_PARAM connectTieNoServant(  ) ;
    
    @Log( level=LogLevel.FINE, id=7 )
    @Message( "Failed to load Tie of class {0}" )
    BAD_PARAM loadTieFailed( @Chain Exception exc, String arg0 ) ;
    
    @Log( level=LogLevel.WARNING, id=1 )
    @Message( "Bad hex digit in string_to_object" )
    DATA_CONVERSION badHexDigit(  ) ;
    
    String unableLocateValueHelper = "Could not locate value helper" ;

    @Log( level=LogLevel.WARNING, id=2 )
    @Message( unableLocateValueHelper )
    @CS( CSValue.MAYBE )
    MARSHAL unableLocateValueHelper(  ) ;

    @Log( level=LogLevel.WARNING, id=2 )
    @Message( unableLocateValueHelper )
    @CS( CSValue.MAYBE )
    MARSHAL unableLocateValueHelper( @Chain Exception exc ) ;
    
    @Log( level=LogLevel.WARNING, id=3 )
    @Message( "Invalid indirection {0}" )
    MARSHAL invalidIndirection( @Chain IOException exc, int indir ) ;
    
    @Log( level=LogLevel.WARNING, id=1 )
    @Message( "{0} did not originate from a connected object" )
    INV_OBJREF objectNotConnected( @Chain Exception exc, String arg0 ) ;
    
    @Log( level=LogLevel.WARNING, id=2 )
    @Message( "Could not load stub for class {0}" )
    INV_OBJREF couldNotLoadStub( String arg0 ) ;
    
    @Log( level=LogLevel.WARNING, id=3 )
    @Message( "Class {0} not exported, or else is actually a JRMP stub" )
    INV_OBJREF objectNotExported( String arg0 ) ;
    
    @Log( level=LogLevel.WARNING, id=1 )
    @Message( "Error in setting object field {0} in {1} to {2}" )
    INTERNAL errorSetObjectField( @Chain Exception exc, 
        String fname, Object obj, Object value ) ;
    
    @Log( level=LogLevel.WARNING, id=2 )
    @Message( "Error in setting boolean field {0} in {1} to {2}" )
    INTERNAL errorSetBooleanField( @Chain Exception exc,
        String fname, Object obj, boolean value ) ;
    
    @Log( level=LogLevel.WARNING, id=3 )
    @Message( "Error in setting byte field {0} in {1} to {2}" )
    INTERNAL errorSetByteField( @Chain Exception exc,
        String fname, Object obj, byte value ) ;
    
    @Log( level=LogLevel.WARNING, id=4 )
    @Message( "Error in setting char field {0} in {1} to {2}" )
    INTERNAL errorSetCharField( @Chain Exception exc,
        String fname, Object obj, char value ) ;
    
    @Log( level=LogLevel.WARNING, id=5 )
    @Message( "Error in setting short field {0} in {1} to {2}" )
    INTERNAL errorSetShortField( @Chain Exception exc,
        String fname, Object obj, short value ) ;
    
    @Log( level=LogLevel.WARNING, id=6 )
    @Message( "Error in setting int field {0} in {1} to {2}" )
    INTERNAL errorSetIntField( @Chain Exception exc,
        String fname, Object obj, int value ) ;
    
    @Log( level=LogLevel.WARNING, id=7 )
    @Message( "Error in setting long field {0} in {1} to {2}" )
    INTERNAL errorSetLongField( @Chain Exception exc,
        String fname, Object obj, long value ) ;
    
    @Log( level=LogLevel.WARNING, id=8 )
    @Message( "Error in setting float field {0} in {1} to {2}" )
    INTERNAL errorSetFloatField( @Chain Exception exc,
        String fname, Object obj, float value ) ;
    
    @Log( level=LogLevel.WARNING, id=9 )
    @Message( "Error in setting double field {0} in {1} to {2}" )
    INTERNAL errorSetDoubleField( @Chain Exception exc,
        String arg0, Object obj, double value ) ;
    
    @Log( level=LogLevel.WARNING, id=10 )
    @Message( "IllegalAccessException while trying to write to field {0}" )
    INTERNAL illegalFieldAccess( String arg0 ) ;
    
    @Log( level=LogLevel.WARNING, id=11 )
    @Message( "State should be saved and reset first" )
    INTERNAL badBeginUnmarshalCustomValue(  ) ;
    
    @Log( level=LogLevel.WARNING, id=12 )
    @Message( "Failure while loading specific Java remote exception class: {0}" )
    INTERNAL classNotFound( String arg0 ) ;
    
    @Log( level=LogLevel.WARNING, id=13 )
    @Message( "Could not find the expected Value Handler implementation "
        + "in the JDK: Wrong JDK Version?" )
    INTERNAL couldNotFindJdkValueHandler( @Chain Exception exc ) ;
    
    @Log( level=LogLevel.FINE, id=14 )
    @Message( " Bad Operation or Bad Invocation Order : The Servant has not "
        + "been associated with an ORB instance" )
    INTERNAL handleSystemException( @Chain SystemException exc ) ;
    
    @Log( level=LogLevel.INFO, id=15 )
    @Message( "This is a test exception with number {0}" )
    INTERNAL testException( int arg0 ) ;
    
    @Log( level=LogLevel.WARNING, id=16 )
    @Message( "This is another test exception with no parameters" )
    @CS( CSValue.MAYBE )
    INTERNAL simpleTestException( @Chain Exception exc  ) ;
    
    @Log( level=LogLevel.WARNING, id=1 )
    @Message( "Unknown System Exception" )
    UNKNOWN unknownSysex(  ) ;
}
