/***
  Copyright (c) 2013 CommonsWare, LLC
  Portions Copyright (C) 2009 The Android Open Source Project
  
  Licensed under the Apache License, Version 2.0 (the "License"); you may
  not use this file except in compliance with the License. You may obtain
  a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

package com.commonsware.cwac.layouts;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class AspectLockedFrameLayout extends FrameLayout {
  private double aspectRatio=0.0;
  private View aspectRatioSource=null;

  public AspectLockedFrameLayout(Context context) {
    super(context);
  }

  public AspectLockedFrameLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  // from com.android.camera.PreviewFrameLayout, with slight
  // modifications

  @Override
  protected void onMeasure(int widthSpec, int heightSpec) {
    double localRatio=aspectRatio;

    if (localRatio == 0.0 && aspectRatioSource != null
        && aspectRatioSource.getHeight() > 0) {
      localRatio=
          (double)aspectRatioSource.getWidth()
              / (double)aspectRatioSource.getHeight();
    }

    if (localRatio == 0.0) {
      super.onMeasure(widthSpec, heightSpec);
    }
    else {
      int lockedWidth=MeasureSpec.getSize(widthSpec);
      int lockedHeight=MeasureSpec.getSize(heightSpec);

      if (lockedWidth == 0 && lockedHeight == 0) {
        throw new IllegalArgumentException(
                                           "Both width and height cannot be zero -- watch out for scrollable containers");
      }

      // Get the padding of the border background.
      int hPadding=getPaddingLeft() + getPaddingRight();
      int vPadding=getPaddingTop() + getPaddingBottom();

      // Resize the preview frame with correct aspect ratio.
      lockedWidth-=hPadding;
      lockedHeight-=vPadding;

      if (lockedHeight > 0 && (lockedWidth > lockedHeight * localRatio)) {
        lockedWidth=(int)(lockedHeight * localRatio + .5);
      }
      else {
        lockedHeight=(int)(lockedWidth / localRatio + .5);
      }

      // Add the padding of the border.
      lockedWidth+=hPadding;
      lockedHeight+=vPadding;

      // Ask children to follow the new preview dimension.
      super.onMeasure(MeasureSpec.makeMeasureSpec(lockedWidth,
                                                  MeasureSpec.EXACTLY),
                      MeasureSpec.makeMeasureSpec(lockedHeight,
                                                  MeasureSpec.EXACTLY));
    }
  }

  public void setAspectRatioSource(View aspectRatioSource) {
    this.aspectRatioSource=aspectRatioSource;
  }

  // from com.android.camera.PreviewFrameLayout, with slight
  // modifications

  public void setAspectRatio(double aspectRatio) {
    if (aspectRatio <= 0.0) {
      throw new IllegalArgumentException(
                                         "aspect ratio must be positive");
    }

    if (this.aspectRatio != aspectRatio) {
      this.aspectRatio=aspectRatio;
      requestLayout();
    }
  }
}
