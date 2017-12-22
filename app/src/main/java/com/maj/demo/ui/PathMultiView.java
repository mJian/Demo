package com.maj.demo.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.widget.ImageView;

/**
 * Created by maJian on 2017/12/12 0012 8:15.
 */

/**
 * 绘制多边形
 */
public class PathMultiView extends ImageView {
    private Paint paint;
    private Path path;
    private int mWidth,mHeight;
    private int count;
    private int color;
    private int radios;

    /**
     *  说明：count=1时 width即为线长度
     * @param context
     * @param width
     * @param height
     * @param count     0:圆形，1:线形，2：椭圆 ，3：三角，4：矩形或者正方形 5及以上：正多边形
     * @param color
     * @param radios    圆半径
     */
    public PathMultiView(Context context, int width, int height, int count, int color, int radios) {
        super(context);
        this.mWidth = width;
        this.mHeight = height;
        this.count = count;
        this.color = color;
        this.radios = radios;
        initPaint();
    }

    private void initPaint() {
        path = new Path();
        paint = new Paint();
        //抗锯齿
        paint.setAntiAlias(true);
        //设置线宽
        paint.setStrokeWidth(1);
        paint.setColor(color);
        /*
         * Android在用画笔的时候有三种Style，分别是
         * Paint.Style.STROKE 只绘制图形轮廓（描边）
         * Paint.Style.FILL 只绘制图形内容
         * Paint.Style.FILL_AND_STROKE 既绘制轮廓也绘制内容
         */
        paint.setStyle(Paint.Style.STROKE);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mWidth,mHeight);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawMultShape(canvas,count,mWidth/2);
    }
    /**
     * @param canvas 画布
     * @param count 绘制几边形
     * @param radius //外圆的半径
     */
    public void drawMultShape(Canvas canvas,int count,float radius){
//        if(count<5){
//            return;
//        }
        if(count == 0){
            //圆
            canvas.translate(radius,radius);//
            canvas.drawCircle(0,0, this.radios, paint);
            return;
        }
        if(count == 1){
            //线
            canvas.drawLine(0, 0, mWidth, 0, paint);
            return;
        }
        if(count == 2){
            //椭圆
            RectF rect = new RectF(0, 0, mWidth, mHeight);
            canvas.drawOval(rect, paint);
            return;
        }
        if(count == 4 && mWidth != mHeight){
            //矩形
            Rect rect = new Rect(0, 0, mWidth, mHeight);
            canvas.drawRect(rect, paint);
            return;
        }
        canvas.translate(radius,radius);//
        for (int i=0;i<count;i++){
            if (i==0){
                path.moveTo(radius*cos(360/count*i),radius*sin(360/count*i));//绘制起点
            }else{
                path.lineTo(radius*cos(360/count*i),radius*sin(360/count*i));
            }
        }
        path.close();
        if (count == 3){
            //三角形
            canvas.rotate(30);
        }
        if (count == 4){
            //三角形
            canvas.rotate(45);
        }
        canvas.drawPath(path,paint);
        //因为我下面不再绘制内容了 所以画布就不恢复了
    }
    float sin(int num){
        return (float) Math.sin(num*Math.PI/180);
    }
    float cos(int num){
        return (float) Math.cos(num*Math.PI/180);
    }

}
