/**
 *
 * Copyright (C) 2009 Adrian Cole <adrian@jclouds.org>
 *
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 */
package org.jclouds.aws.s3.commands.options;

import org.jclouds.aws.s3.S3Headers;
import org.jclouds.aws.s3.domain.S3Bucket.Metadata.LocationConstraint;
import org.jclouds.aws.s3.domain.acl.CannedAccessPolicy;
import org.jclouds.http.options.BaseHttpRequestOptions;

import static com.google.common.base.Preconditions.*;

/**
 * Contains options supported in the REST API for the PUT bucket operation. <h2>
 * Usage</h2> The recommended way to instantiate a PutBucketOptions object is to
 * statically import PutBucketOptions.Builder.* and invoke a static creation
 * method followed by an instance mutator (if needed):
 * <p/>
 * <code>
 * import static org.jclouds.aws.s3.commands.options.PutBucketOptions.Builder.*
 * import static org.jclouds.aws.s3.domain.S3Bucket.MetaData.LocationConstraint.*;
 * import org.jclouds.aws.s3.S3Connection;
 * 
 * S3Connection connection = // get connection
 * Future<Boolean> createdInEu = connection.putBucketIfNotExists("bucketName",createIn(EU));
 * <code>
 * 
 * Description of parameters taken from {@link http://docs.amazonwebservices.com/AmazonS3/2006-03-01/index.html?RESTBucketPUT.html}
 * 
 * @author Adrian Cole
 * 
 */
public class PutBucketOptions extends BaseHttpRequestOptions {
    public static final PutBucketOptions NONE = new PutBucketOptions();
    private CannedAccessPolicy acl = CannedAccessPolicy.PRIVATE;
    private LocationConstraint constraint;

    /**
     * Depending on your latency and legal requirements, you can specify a
     * location constraint that will affect where your data physically resides.
     * You can currently specify a Europe (EU) location constraint.
     */
    public PutBucketOptions createIn(LocationConstraint constraint) {
	this.constraint = checkNotNull(constraint, "constraint");
	this.payload = String
		.format(
			"<CreateBucketConfiguration><LocationConstraint>%1s</LocationConstraint></CreateBucketConfiguration>",
			constraint.toString());
	return this;
    }

    /**
     * Override the default ACL (private) with the specified one.
     * 
     * @see CannedAccessPolicy
     */
    public PutBucketOptions withBucketAcl(CannedAccessPolicy acl) {
	this.acl = checkNotNull(acl, "acl");
	if (!acl.equals(CannedAccessPolicy.PRIVATE))
	    this.replaceHeader(S3Headers.CANNED_ACL, acl.toString());
	return this;
    }

    /**
     * @see PutBucketOptions#withBucketAcl(CannedAccessPolicy)
     */
    public CannedAccessPolicy getAcl() {
	return acl;
    }

    /**
     * @see PutBucketOptions#createIn(LocationConstraint)
     */
    public LocationConstraint getLocationConstraint() {
	return constraint;
    }

    public static class Builder {
	/**
	 * @see PutBucketOptions#createIn(LocationConstraint)
	 */
	public static PutBucketOptions createIn(LocationConstraint constraint) {
	    PutBucketOptions options = new PutBucketOptions();
	    return options.createIn(constraint);
	}

	/**
	 * @see PutBucketOptions#withBucketAcl(CannedAccessPolicy)
	 */
	public static PutBucketOptions withBucketAcl(CannedAccessPolicy acl) {
	    PutBucketOptions options = new PutBucketOptions();
	    return options.withBucketAcl(acl);
	}
    }
}