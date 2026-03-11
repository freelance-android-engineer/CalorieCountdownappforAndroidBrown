package ese.com.caloriecountdownappforandroidbrown.ui.debitactivitycif13;

//import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import androidx.core.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



import androidx.lifecycle.ViewModelProviders;
import ese.com.caloriecountdownappforandroidbrown.R;

public class Debit_Activity_CIF13Fragment extends Fragment {

    private DebitActivityCIF13ViewModel mViewModel;
    public static final String TOTAL_DEBIT_VALUE = "Total Debit Countdown Value";
    private Button mDebit;
    private ese.com.caloriecountdownappforandroidbrown.Food_Item_CIF4 mCountdown;

    public static Debit_Activity_CIF13Fragment newInstance() {
        return new Debit_Activity_CIF13Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.debit__activity__c_i_f13_fragment, container, false);

        //View v = inflater.inflate(R.layout.fragment_debit__activity__cif13, container, false);
        //mDebit = (Button) v.findViewById(R.id.button12);
        //mDebit.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        EditText editText63 = (EditText) getActivity().findViewById(R.id.edit_text63);
        //        EditText editText64 = (EditText) getActivity().findViewById(R.id.edit_text64);
        //        Spinner spinner = (Spinner) getActivity().findViewById(R.id.spincity);
               // mCountdown = new Fitness_Item_CIF5();
               // mCountdown.setmUserWeightlbs(new RoundingCIF13().StringToFloat(editText63.getText().toString()));
               // mCountdown.setmMinutesPerformed((int) (new RoundingCIF13().StringToFloat(editText64.getText().toString())));
               // String s = spinner.getSelectedItem().toString();
               // mCountdown.ConvertSpinnerItem(s);
               // BackToParent(GetCountdownDebit(mCountdown));
          //  }
      //  });
      //  return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DebitActivityCIF13ViewModel.class);
        // TODO: Use the ViewModel
    }





/*
    private int GetCountdownDebit(Fitness_Item_CIF5 fizz)
    {
        //Alogrithm Engineering -> Android : GetCountdownDebit() (Track, hence eta date Transition Date = 5pm December 12, 2011 (strictly on Track)
        //Step 1. return weight * min * fizz data value.

        int debit = fizz.CalculateCountdown();
        SummaryBoxCIF12 summy = SummaryBoxCIF12.get(getActivity());
        summy.Set_mFitnessItems(fizz);

        return debit;

    }

    private void BackToParent(int debit)
    {
        //return intent call with intent packed with value of Debit or Credit and Summary String as well as summarybox ready
        Intent i2 = new Intent();
        i2.putExtra(TOTAL_DEBIT_VALUE, debit);
        //i2.putExtra(Food_Diary_Sheet_CIF3.SUMMATION_TEXT, summary_box.GetDebitSummaryString());
        getActivity().setResult(getActivity().RESULT_OK, i2);
        getActivity().finish();

    }*/

}