package koreatech.teamproject_propt;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class GraphActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph);
    }

    public void onClick(View v){

        int viewId = v.getId();

        if(viewId == R.id.datebutton){
            Calendar cal = Calendar.getInstance();

            DatePickerDialog.OnDateSetListener mDateSetListener =
                    new DatePickerDialog.OnDateSetListener(){
                        @Override
                        public void onDateSet(DatePicker datePicker, int yy, int mm, int dd){

                            TextView tv = findViewById(R.id.datefill);
                        }

                    };
            DatePickerDialog dpDialog =new DatePickerDialog(this, mDateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DATE));
            dpDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            dpDialog.show();
        }
        else if(viewId == R.id.datebutton){
            TextView tv = findViewById(R.id.datefill);
            Calendar cal = Calendar.getInstance();
            tv.setText(cal.get(Calendar.YEAR) + "-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DATE));

        }

    }

}
