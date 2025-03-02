/*
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
 */

package com.jfoenix.skins;

import javafx.scene.control.skin.NestedTableColumnHeader;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.control.skin.TableViewSkinBase;

/**
 * @author Shadi Shaheen
 */
public class JFXTableHeaderRow
{

   private TableViewSkinBase tableSkin;
   private TableHeaderRow headerRow;

   public JFXTableHeaderRow(final TableViewSkinBase skin)
   {
      this.tableSkin = skin;
      headerRow = new TableHeaderRow(skin)
      {
         // protected to allow subclasses to provide a custom root header
         protected NestedTableColumnHeader createRootHeader()
         {
            return new JFXNestedTableColumnHeader(null);
         }
      };
   }

   public TableHeaderRow getHeaderRow()
   {
      return headerRow;
   }

}
