package com.br.ipad.isc.gui;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.SurfaceView;

import com.br.ipad.isc.util.Util;

public class CustomDrawableView extends SurfaceView {
	private ShapeDrawable circulo;

	public CustomDrawableView(Context context) {
		super(context);
	}

	protected void onDraw(Canvas canvas) {
		circulo.draw(canvas);
	}
	
	public CustomDrawableView(Context context, AttributeSet atr){
		super(context, atr);

		circulo = new ShapeDrawable(new OvalShape());
		Util.desenhaCirculo(context, circulo);
	}
}