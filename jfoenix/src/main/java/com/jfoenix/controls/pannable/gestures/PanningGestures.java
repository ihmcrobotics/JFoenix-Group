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

package com.jfoenix.controls.pannable.gestures;

import com.jfoenix.controls.pannable.base.IPannablePane;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 * Add panning gestures to {@link IPannablePane} region
 *
 * @author Shadi Shaheen
 * @version 1.0
 * @since 2020-04-30
 */
public class PanningGestures<T extends Node & IPannablePane>
{

   /**
    * Drag context used to store mouse drag data
    */
   private static class DragContext
   {
      double mouseAnchorX;
      double mouseAnchorY;
      double translateAnchorX;
      double translateAnchorY;
   }

   private T canvas;
   private final DragContext sceneDragContext = new DragContext();

   public PanningGestures(T canvas)
   {
      this.canvas = canvas;
   }

   /***************************************************************************
    * * Panning control properties * *
    **************************************************************************/

   private final DoubleProperty minScaleProperty = new SimpleDoubleProperty(0.1d);

   public double getMinScale()
   {
      return minScaleProperty.get();
   }

   public void setMinScale(double minScale)
   {
      minScaleProperty.set(minScale);
   }

   public DoubleProperty minScaleProperty()
   {
      return minScaleProperty;
   }

   private final DoubleProperty maxScaleProperty = new SimpleDoubleProperty(10.0d);

   public double getMaxScale()
   {
      return maxScaleProperty.get();
   }

   public DoubleProperty maxScaleProperty()
   {
      return maxScaleProperty;
   }

   public void setMaxScale(double maxScale)
   {
      maxScaleProperty.set(maxScale);
   }

   private final DoubleProperty zoomSpeedProperty = new SimpleDoubleProperty(1.2d);

   public double getZoomSpeed()
   {
      return zoomSpeedProperty.get();
   }

   public DoubleProperty zoomSpeedProperty()
   {
      return zoomSpeedProperty;
   }

   public void setZoomSpeed(double zoomSpeed)
   {
      zoomSpeedProperty.set(zoomSpeed);
   }

   private SimpleBooleanProperty useViewportGestures = null;

   public final BooleanProperty useViewPortGesturesProperty()
   {
      return useViewportGestures;
   }

   // set true to bind panning to canvas size
   private boolean bound = false;

   public boolean isBound()
   {
      return bound;
   }

   public void setBound(boolean bound)
   {
      this.bound = bound;
   }

   /***************************************************************************
    * * Panning listeners * *
    **************************************************************************/

   private final EventHandler<MouseEvent> onMousePressedEventHandler = event ->
   {
      // right mouse button => panning
      if (!event.isSecondaryButtonDown())
      {
         return;
      }
      sceneDragContext.mouseAnchorX = event.getSceneX();
      sceneDragContext.mouseAnchorY = event.getSceneY();
      sceneDragContext.translateAnchorX = canvas.getTranslateX();
      sceneDragContext.translateAnchorY = canvas.getTranslateY();
   };

   private final EventHandler<MouseEvent> onMouseDraggedEventHandler = event ->
   {
      // right mouse button => panning
      if (!event.isSecondaryButtonDown())
      {
         return;
      }
      final Bounds parentLayoutBounds = canvas.getParent().getLayoutBounds();
      final Bounds boundsInParent = canvas.getBoundsInParent();
      // compute new trans X
      double newTransX = sceneDragContext.translateAnchorX + event.getSceneX() - sceneDragContext.mouseAnchorX;
      canvas.setTranslateX(bound(newTransX, parentLayoutBounds.getWidth(), boundsInParent.getWidth()));
      // compute new trans Y
      double newTransY = sceneDragContext.translateAnchorY + event.getSceneY() - sceneDragContext.mouseAnchorY;
      canvas.setTranslateY(bound(newTransY, parentLayoutBounds.getHeight(), boundsInParent.getHeight()));
      event.consume();
   };

   private double bound(double newTrans, double parentSize, double canvasSize)
   {
      if (!bound)
      {
         return newTrans;
      }
      double lowerBound = parentSize - canvasSize - 20 * canvas.getScale();
      if (parentSize > canvasSize)
      {
         newTrans = 1;
      }
      else
      {
         newTrans = inBound(lowerBound, 1, newTrans);
      }
      return newTrans;
   }

   private double inBound(double lowerBound, double upperBound, double val)
   {
      if (val < lowerBound)
      {
         val = lowerBound;
      }
      else if (val > upperBound)
      {
         val = upperBound;
      }
      return val;
   }

   /**
    * Zoom handler: responsible for zooming to a pivot coordinate
    */
   private final EventHandler<ScrollEvent> onScrollEventHandler = event ->
   {
      double scale = canvas.getScale(); // currently we only use Y, same value is used for X
      if (event.getDeltaY() < 0)
      {
         scale /= getZoomSpeed();
      }
      else
      {
         scale *= getZoomSpeed();
      }
      scale = clamp(scale, minScaleProperty.get(), maxScaleProperty.get());
      Point2D currentPoint = canvas.parentToLocal(event.getX(), event.getY());
      canvas.setScale(scale);
      Point2D newPoint = canvas.localToParent(currentPoint);

      final Bounds parentLayoutBounds = canvas.getParent().getLayoutBounds();
      final Bounds boundsInParent = canvas.getBoundsInParent();
      double lowerBound = parentLayoutBounds.getWidth() - boundsInParent.getWidth() - 20;
      canvas.setTranslateX(bound(canvas.getTranslateX() - (newPoint.getX() - event.getX()), parentLayoutBounds.getWidth(), boundsInParent.getWidth()));
      canvas.setTranslateY(bound(canvas.getTranslateY() - (newPoint.getY() - event.getY()), parentLayoutBounds.getHeight(), boundsInParent.getHeight()));
      event.consume();
   };

   public static double clamp(double value, double min, double max)
   {
      if (Double.compare(value, min) < 0)
      {
         return min;
      }
      if (Double.compare(value, max) > 0)
      {
         return max;
      }
      return value;
   }

   /***************************************************************************
    * * Attach methods * *
    **************************************************************************/

   public static <T extends Node & IPannablePane> PanningGestures<T> attachViewPortGestures(T pannableCanvas)
   {
      return attachViewPortGestures(pannableCanvas, false);
   }

   public static <T extends Node & IPannablePane> PanningGestures<T> attachViewPortGestures(T pannableCanvas, boolean configurable)
   {
      PanningGestures<T> panningGestures = new PanningGestures<>(pannableCanvas);
      if (configurable)
      {
         panningGestures.useViewportGestures = new SimpleBooleanProperty(true);
         panningGestures.useViewportGestures.addListener((o, oldVal, newVal) ->
         {
            final Parent parent = pannableCanvas.parentProperty().get();
            if (parent == null)
            {
               return;
            }
            if (newVal)
            {
               parent.addEventHandler(MouseEvent.MOUSE_PRESSED, panningGestures.onMousePressedEventHandler);
               parent.addEventHandler(MouseEvent.MOUSE_DRAGGED, panningGestures.onMouseDraggedEventHandler);
               parent.addEventHandler(ScrollEvent.ANY, panningGestures.onScrollEventHandler);
            }
            else
            {
               parent.removeEventHandler(MouseEvent.MOUSE_PRESSED, panningGestures.onMousePressedEventHandler);
               parent.removeEventHandler(MouseEvent.MOUSE_DRAGGED, panningGestures.onMouseDraggedEventHandler);
               parent.removeEventHandler(ScrollEvent.ANY, panningGestures.onScrollEventHandler);
            }
         });
      }
      pannableCanvas.parentProperty().addListener((o, oldVal, newVal) ->
      {
         if (oldVal != null)
         {
            oldVal.removeEventHandler(MouseEvent.MOUSE_PRESSED, panningGestures.onMousePressedEventHandler);
            oldVal.removeEventHandler(MouseEvent.MOUSE_DRAGGED, panningGestures.onMouseDraggedEventHandler);
            oldVal.removeEventHandler(ScrollEvent.ANY, panningGestures.onScrollEventHandler);
         }
         if (newVal != null)
         {
            newVal.addEventHandler(MouseEvent.MOUSE_PRESSED, panningGestures.onMousePressedEventHandler);
            newVal.addEventHandler(MouseEvent.MOUSE_DRAGGED, panningGestures.onMouseDraggedEventHandler);
            newVal.addEventHandler(ScrollEvent.ANY, panningGestures.onScrollEventHandler);
         }
      });
      return panningGestures;
   }
}
