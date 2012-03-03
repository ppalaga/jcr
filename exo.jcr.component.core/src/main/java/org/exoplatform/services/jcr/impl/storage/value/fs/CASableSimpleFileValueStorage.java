/*
 * Copyright (C) 2009 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.exoplatform.services.jcr.impl.storage.value.fs;

import org.exoplatform.commons.utils.Tools;
import org.exoplatform.services.jcr.config.RepositoryConfigurationException;
import org.exoplatform.services.jcr.impl.storage.value.ValueDataResourceHolder;
import org.exoplatform.services.jcr.impl.storage.value.cas.ValueContentAddressStorage;
import org.exoplatform.services.jcr.impl.util.io.FileCleaner;
import org.exoplatform.services.jcr.storage.value.ValueIOChannel;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by The eXo Platform SAS .
 * 
 * @author Gennady Azarenkov
 * @version $Id: CASableSimpleFileValueStorage.java 34801 2009-07-31 15:44:50Z dkatayev $
 */

public class CASableSimpleFileValueStorage extends FileValueStorage
{

   private ValueContentAddressStorage vcas;

   private String digestAlgo;

   public CASableSimpleFileValueStorage(FileCleaner cleaner)
   {
      super(cleaner);
   }

   @Override
   public void init(Properties props, ValueDataResourceHolder resources) throws IOException,
      RepositoryConfigurationException
   {
      super.init(props, resources);

      this.digestAlgo = props.getProperty(ValueContentAddressStorage.DIGEST_ALGO_PARAM);
      String vcasType = props.getProperty(ValueContentAddressStorage.VCAS_TYPE_PARAM);

      // get other vcas specific props and make VCAS
      try
      {
         vcas = (ValueContentAddressStorage)Tools.forName(vcasType, this).newInstance();
      }
      catch (Exception e)
      {
         throw new RepositoryConfigurationException("VCAS Storage class load error " + e, e);
      }
      vcas.init(props);
   }

   @Override
   public ValueIOChannel openIOChannel() throws IOException
   {
      return new CASableSimpleFileIOChannel(rootDir, cleaner, id, resources, vcas, digestAlgo);
   }

}
