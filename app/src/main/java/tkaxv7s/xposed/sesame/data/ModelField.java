package tkaxv7s.xposed.sesame.data;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import tkaxv7s.xposed.sesame.R;
import tkaxv7s.xposed.sesame.util.TypeUtil;

import java.io.Serializable;
import java.lang.reflect.Type;

@Data
public class ModelField<T> implements Serializable {

    @JsonIgnore
    private final Type valueType;

    @JsonIgnore
    private String code;

    @JsonIgnore
    private String name;

    @JsonIgnore
    protected T defaultValue;

    protected volatile T value;

    public ModelField() {
        valueType = TypeUtil.getTypeArgument(this.getClass().getGenericSuperclass(), 0);
    }

    public ModelField(T value) {
        this(null, null, value);
    }

    public ModelField(String code, String name, T value) {
        this();
        this.code = code;
        this.name = name;
        this.defaultValue = value;
        setObjectValue(value);
    }

    public void setObjectValue(Object value) {
        this.value = (T) value;
    }

    @JsonIgnore
    public String getType() {
        return "DEFAULT";
    }

    @JsonIgnore
    public Object getExpandKey() {
        return null;
    }

    @JsonIgnore
    public Object getExpandValue() {
        return null;
    }

    @JsonIgnore
    public String getConfigValue() {
        return String.valueOf(getValue());
    }

    @JsonIgnore
    public void setConfigValue(String value) {
        setObjectValue(value);
    }

    public void reset() {
        value = defaultValue;
    }

    @JsonIgnore
    public View getView(Context context) {
        TextView btn = new TextView(context);
        btn.setText(getName());
        btn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btn.setTextColor(Color.parseColor("#008175"));
        btn.setBackground(context.getResources().getDrawable(R.drawable.button));
        btn.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        btn.setMinHeight(150);
        btn.setMaxHeight(180);
        btn.setPaddingRelative(40, 0, 40, 0);
        btn.setAllCaps(false);
        btn.setOnClickListener(v -> Toast.makeText(context, "无配置项", Toast.LENGTH_SHORT).show());
        return btn;
    }

}
