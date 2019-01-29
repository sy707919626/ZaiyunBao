package com.lulian.Zaiyunbao.ui.activity.subleaseorder;


import android.graphics.Color;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/11/16.
 */

public class SubleaseDeliveryActivity extends BaseActivity {

    // 创建生成的文件地址
    private static final String newPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/songhuo.doc";
    private static final String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/doc";
    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.sublease_delivery_data)
    TextView subleaseDeliveryData;
    @BindView(R.id.sublease_delivery_orderno)
    TextView subleaseDeliveryOrderno;
    @BindView(R.id.sublease_delivery_name)
    TextView subleaseDeliveryName;
    @BindView(R.id.sublease_delivery_phone)
    TextView subleaseDeliveryPhone;
    @BindView(R.id.sublease_delivery_address)
    TextView subleaseDeliveryAddress;
    @BindView(R.id.sublease_delivery_sum)
    TextView subleaseDeliverySum;
    @BindView(R.id.sublease_delivery_btn)
    Button subleaseDeliveryBtn;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_sublease_delivery;
    }

    @Override
    protected void init() {
        ImmersionBar.with(this)
                .titleBar(R.id.detail_bar_title)
                .titleBarMarginTop(R.id.detail_bar_title)
                .navigationBarColorInt(Color.WHITE)
                .statusBarDarkFont(true, 0.5f)
                .navigationBarDarkIcon(true, 0.5f)
                .init();

        textDetailContent.setText("送货单");
        textDetailRight.setText("转发");

        subleaseDeliveryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save(v);
                RxToast.success("送货单导出成功！");
            }
        });
    }

    /**
     * newFile 生成文件
     * map 要填充的数据
     */
    public void writeDoc(InputStream in, File newFile, Map<String, String> map) {
        try {

            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }

            HWPFDocument hdt = new HWPFDocument(in);
            // Fields fields = hdt.getFields();
            // 读取word文本内容
            Range range = hdt.getRange();
            // System.out.println(range.text());

            // 替换文本内容
            for (Map.Entry<String, String> entry : map.entrySet()) {
                range.replaceText(entry.getKey(), entry.getValue());
            }
            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
            FileOutputStream out = new FileOutputStream(newFile, true);
            hdt.write(ostream);
            // 输出字节流
            out.write(ostream.toByteArray());
            out.close();
            ostream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 这个是Button的点击事件
     *
     * @param view
     */
    public void Save(View view) {
        try {
            //从assets读取我们的Word模板
            InputStream is = getAssets().open("deliverynote.doc");
            //创建生成的文件
            File newFile = new File(newPath);
            Map<String, String> map = new HashMap<String, String>();
            map.put("$date$", "张磊");
            map.put("$order$", "男");
            map.put("$name$", "aaa");
            map.put("$phone$", "111116546");
            map.put("$address$", "上海市杨浦区xx路xx号");
            map.put("$sum$", "12");
            writeDoc(is, newFile, map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
