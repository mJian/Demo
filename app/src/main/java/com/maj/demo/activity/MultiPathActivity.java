package com.maj.demo.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.maj.demo.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class MultiPathActivity extends AppCompatActivity {

    private Context mContext;

    @ViewInject(R.id.et_multi_path)
    EditText etPath;

    @ViewInject(R.id.ll_multi_view)
    LinearLayout llMulti;
    @ViewInject(R.id.img_multi_path)
    ImageView imgMulti;

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_path);
        x.view().inject(this);
        mContext = this;

        etPath.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.e("MultiPathActivity", "输入结果"+ etPath.getText().toString().trim());
                String res = etPath.getText().toString().trim();
                if (!TextUtils.isEmpty(res)){
                    int count = Integer.valueOf(res);
                    if (bitmap != null){
                        bitmap.recycle();
                        bitmap = null;
                    }
                    bitmap = getBitmapFromCanvasForMultiShape(count, 200, 200, Color.RED, 100);
                    imgMulti.setImageBitmap(bitmap);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_test:
                int[][] points = new int[200][200];
                for (int i=0; i<200; i++){
                    for (int j=0;j<200;j++){
                        int color = bitmap.getPixel(i,j);
                        int red = Color.red(color);
                        int green = Color.green(color);
                        int blue = Color.blue(color);
                        if(red !=0 || green !=0 || blue!=0){
//                            Log.e("MultiPathActivity", "Color.RED.red=" +Color.red(Color.RED)+",red="+red+",green="+green+",blue="+blue);
                            Log.e("MultiPathActivity", "x=" +i+",y="+j);
                            points[i][j] = 1;
                        }
                    }
                }

                Bitmap bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);

                Canvas canvas = new Canvas(bitmap);
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                for (int i=0; i<200;){
                    for (int j=0;j<200;){
                        if (points[i][j] == 1){
                            canvas.drawPoint(i, j, paint);
                        }
                        if(j==198){
                            j++;
                        }else {
                            j+=2;
                        }
                    }
                    if(i==198){
                        i++;
                    }else {
                        i+=2;
                    }
                }

                imgMulti.setImageBitmap(bitmap);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * 绘制多边形
     * Author: maJian
     * Time: 2017/12/14 0014 8:41
     * @param count             0:圆形，1:线形，2：椭圆 ，3：三角，4：矩形或者正方形 5及以上：正多边形
     * @param shapeWidth        图形的宽
     * @param shapeHeight       图形高
     * @param color              画笔颜色
     * @param shapeRadios       圆半径|椭圆
     * @return                  对应多边形的Bitmap
     */
    public static Bitmap getBitmapFromCanvasForMultiShape(int count, int shapeWidth, int shapeHeight, int color, int shapeRadios){
        Bitmap bitmap;
        Canvas canvas;
        Path path = new Path();
        Paint paint = new Paint();
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

        bitmap = Bitmap.createBitmap(shapeWidth, shapeHeight, Bitmap.Config.ARGB_8888);
        bitmap.setHasAlpha(true);
        canvas = new Canvas(bitmap);

        if(count == 0){
            //圆
            canvas.translate(shapeRadios,shapeRadios);//
            canvas.drawCircle(0,0, shapeRadios, paint);
            return bitmap;
        }
        if(count == 1){
            //线
            canvas.drawLine(0, 0, shapeWidth, 0, paint);
            return bitmap;
        }
        if(count == 2){
            //椭圆
            RectF rect = new RectF(0, 0, shapeWidth, shapeHeight/2);
            canvas.drawOval(rect, paint);
            return bitmap;
        }
        if(count == 4 && shapeWidth != shapeHeight){
            //矩形
            Rect rect = new Rect(0, 0, shapeWidth, shapeHeight);
            canvas.drawRect(rect, paint);
            return bitmap;
        }
        int radius = shapeWidth / 2;
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
            //正方形
            canvas.rotate(45);
        }
        canvas.drawPath(path,paint);
        return bitmap;
    }

    public static float sin(int num){
        return (float) Math.sin(num*Math.PI/180);
    }
    public static float cos(int num){
        return (float) Math.cos(num*Math.PI/180);
    }
}
