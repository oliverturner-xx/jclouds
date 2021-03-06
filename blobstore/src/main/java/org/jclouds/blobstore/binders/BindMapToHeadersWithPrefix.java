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

package org.jclouds.blobstore.binders;

import static org.jclouds.blobstore.reference.BlobStoreConstants.PROPERTY_USER_METADATA_PREFIX;

import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.jclouds.http.HttpRequest;
import org.jclouds.rest.Binder;

/**
 * 
 * @author Adrian Cole
 */
@Singleton
public class BindMapToHeadersWithPrefix implements Binder {
   private final String metadataPrefix;

   @Inject
   public BindMapToHeadersWithPrefix(@Named(PROPERTY_USER_METADATA_PREFIX) String metadataPrefix) {
      this.metadataPrefix = metadataPrefix;
   }

   @SuppressWarnings("unchecked")
   public void bindToRequest(HttpRequest request, Object payload) {
      Map<String, String> userMetadata = (Map<String, String>) payload;
      for (Entry<String, String> entry : userMetadata.entrySet()) {
         if (entry.getKey().startsWith(metadataPrefix)) {
            request.getHeaders().put(entry.getKey().toLowerCase(), entry.getValue());
         } else {
            request.getHeaders().put((metadataPrefix + entry.getKey()).toLowerCase(),
                     entry.getValue());
         }
      }
   }

}
