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

package org.jclouds.aws.s3.xml;

import org.jclouds.aws.s3.domain.Payer;
import org.jclouds.http.functions.ParseSax;

/**
 * Parses the response from Amazon S3 GET Request Payment
  * <p/>
 * RequestPaymentConfiguration is the document we expect to parse.
 * 
 * @see <a href= "http://docs.amazonwebservices.com/AmazonS3/latest/RESTrequestPaymentGET.html" />
 * @author Adrian Cole
 */
public class PayerHandler extends ParseSax.HandlerWithResult<Payer> {
   private StringBuilder currentText = new StringBuilder();
   private Payer constraint;

   public Payer getResult() {
      return constraint;
   }

   public void endElement(String uri, String name, String qName) {
      constraint = Payer.fromValue(currentText.toString().trim());
   }

   public void characters(char ch[], int start, int length) {
      currentText.append(ch, start, length);
   }
}
