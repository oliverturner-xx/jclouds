/**
 *
 * Copyright (C) 2010 Cloud Conscious, LLC. <info@cloudconscious.com>
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */

package org.jclouds.vcloud.xml;

import static org.testng.Assert.assertEquals;

import java.io.InputStream;
import java.net.URI;

import org.jclouds.http.functions.ParseSax;
import org.jclouds.http.functions.ParseSax.Factory;
import org.jclouds.http.functions.config.SaxParserModule;
import org.jclouds.vcloud.VCloudMediaType;
import org.jclouds.vcloud.domain.Status;
import org.jclouds.vcloud.domain.VApp;
import org.jclouds.vcloud.domain.Vm;
import org.jclouds.vcloud.domain.internal.ReferenceTypeImpl;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Tests behavior of {@code VAppHandler}
 * 
 * @author Adrian Cole
 */
@Test(groups = "unit", testName = "vcloud.VAppHandlerTest")
public class VAppHandlerTest {
   public void testRhelOffStatic() {
      InputStream is = getClass().getResourceAsStream("/vapp-rhel-off-static.xml");
      Injector injector = Guice.createInjector(new SaxParserModule());
      Factory factory = injector.getInstance(ParseSax.Factory.class);
      VApp result = factory.create(injector.getInstance(VAppHandler.class)).parse(is);
      assertEquals(result.getName(), "vApp_acole_2");
      assertEquals(result.getHref(), URI.create("https://vcenterprise.bluelock.com/api/v1.0/vApp/vapp-607806320"));
      assertEquals(result.getType(), "application/vnd.vmware.vcloud.vApp+xml");
      assertEquals(result.getStatus(), Status.OFF);
      assertEquals(result.getVDC(), new ReferenceTypeImpl(null, VCloudMediaType.VDC_XML, URI
               .create("https://vcenterprise.bluelock.com/api/v1.0/vdc/1014839439")));
      assertEquals(result.getDescription(), null);
      assertEquals(result.getTasks(), ImmutableList.of());
      assert result.isOvfDescriptorUploaded();
      Vm vm = Iterables.getOnlyElement(result.getChildren());
      VmHandlerTest.checkVm(vm);
   }

}
