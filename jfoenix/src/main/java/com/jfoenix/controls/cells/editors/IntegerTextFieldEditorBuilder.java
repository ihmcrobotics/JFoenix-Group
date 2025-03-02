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

package com.jfoenix.controls.cells.editors;

import com.jfoenix.utils.JFXUtilities;
import com.jfoenix.validation.IntegerValidator;
import com.jfoenix.validation.base.ValidatorBase;

/**
 * <h1>Text field cell editor (numbers only)</h1> this an example of the cell editor, it creates a
 * JFXTextField node to allow the user to edit the cell value
 * <p>
 *
 * @author Shadi Shaheen
 * @version 1.0
 * @since 2016-03-09
 */
public class IntegerTextFieldEditorBuilder extends TextFieldEditorBase<Integer>
{

   public IntegerTextFieldEditorBuilder(ValidatorBase... validators)
   {
      super(JFXUtilities.concat(new ValidatorBase[] {new IntegerValidator()}, validators, len -> new ValidatorBase[len]));
   }

   @Override
   public Integer getValue()
   {
      return Integer.valueOf(textField.getText());
   }
}
