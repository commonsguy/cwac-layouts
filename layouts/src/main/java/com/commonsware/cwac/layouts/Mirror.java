/***
  Copyright (c) 2013-2014 CommonsWare, LLC
  
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
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * A View that implements MirrorSink and renders the supplied bitmaps to its
 * own contents. When connected to a MirroringFrameLayout, Mirror will aim to
 * show the same contents as is in the MirroringFrameLayout, at the same aspect
 * ratio, though possibly at a different size.
 *
 * Principally, MirroringFrameLayout and Mirror are designed for use with
 * Android's Presentation system. The MirroringFrameLayout would be part of the
 * UI of the activity on the mobile device, allowing for user interaction. The
 * Mirror would be used in the Presentation to show an audience (e.g., via a
 * projector) what is shown inside the MirroringFrameLayout on the mobile
 * device.
 */
public class Mirror extends View implements MirrorSink {
  private Rect rect=new Rect();
  private Bitmap bmp=null;

  /**
   * {@inheritDoc}
   */
  public Mirror(Context context) {
    super(context);
  }

  /**
   * {@inheritDoc}
   */
  public Mirror(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  /**
   * {@inheritDoc}
   */
  public Mirror(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void update(Bitmap bmp) {
    this.bmp=bmp;
    invalidate();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    if (bmp != null) {
      getDrawingRect(rect);

      calcCenter(rect.width(), rect.height(), bmp.getWidth(),
                 bmp.getHeight(), rect);
      canvas.drawBitmap(bmp, null, rect, null);
    }
  }

  // based upon http://stackoverflow.com/a/14679729/115145

  static void calcCenter(int vw, int vh, int iw, int ih, Rect out) {
    double scale=
        Math.min((double)vw / (double)iw, (double)vh / (double)ih);

    int h=(int)(scale * ih);
    int w=(int)(scale * iw);
    int x=((vw - w) >> 1);
    int y=((vh - h) >> 1);

    out.set(x, y, x + w, y + h);
  }
}
