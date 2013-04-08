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
import android.view.View;

public class Mirror extends View {
  private MirroringFrameLayout source=null;
  private Rect rect=new Rect();

  public Mirror(Context context) {
    super(context);
  }

  public Mirror(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public Mirror(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public void setSource(MirroringFrameLayout source) {
    this.source=source;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    getDrawingRect(rect);

    Bitmap cache=source.getLastBitmap();

    calcCenter(rect.width(), rect.height(), cache.getWidth(),
               cache.getHeight(), rect);
    canvas.drawBitmap(cache, null, rect, null);
  }

  // based upon http://stackoverflow.com/a/14679729/115145

  private static void calcCenter(int vw, int vh, int iw, int ih,
                                 Rect out) {
    double scale=
        Math.min((double)vw / (double)iw, (double)vh / (double)ih);

    int h=(int)(scale * ih);
    int w=(int)(scale * iw);
    int x=((vw - w) >> 1);
    int y=((vh - h) >> 1);

    out.set(x, y, x + w, y + h);
  }
}
