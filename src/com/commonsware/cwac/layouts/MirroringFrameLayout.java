/***
  Copyright (c) 2013 CommonsWare, LLC
  
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
import android.widget.FrameLayout;

public class MirroringFrameLayout extends FrameLayout {
  private Mirror mirror=null;
  private Bitmap bmp=null;
  private Canvas bmpBackedCanvas=null;
  private Rect rect=new Rect();

  public MirroringFrameLayout(Context context) {
    this(context, null);
  }

  public MirroringFrameLayout(Context context, AttributeSet attrs) {
    super(context, attrs);

    setWillNotDraw(false);
  }

  public void setMirror(Mirror mirror) {
    this.mirror=mirror;
    mirror.setSource(this);
  }

  @Override
  public void draw(Canvas canvas) {
    bmp.eraseColor(0);

    super.draw(bmpBackedCanvas);

    getDrawingRect(rect);
    canvas.drawBitmap(bmp, null, rect, null);

    if (mirror != null) {
      mirror.invalidate();
    }
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    if (bmp == null || bmp.getWidth() != w || bmp.getHeight() != h) {
      if (bmp != null) {
        bmp.recycle();
      }

      bmp=Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
      bmpBackedCanvas=new Canvas(bmp);
    }

    super.onSizeChanged(w, h, oldw, oldh);
  }

  Bitmap getLastBitmap() {
    return(bmp);
  }
}
