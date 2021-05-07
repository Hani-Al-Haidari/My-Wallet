package com.annhienktuit.mywallet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_interest_rate.*

class InterestRateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interest_rate)

        if (supportActionBar != null) {
            supportActionBar?.hide();
        }

        TypeOfInterestLayout.setOnClickListener{
            var dialog = TypeOfInterestDialog()

            dialog.show(supportFragmentManager, "TypeOfInterestDialog")

            //TypeOfInterest.text = dialog.getInterestType()
        }

        periodOfTimeLayout.setOnClickListener{
            var dialog = PeriodOfTimeDialog()

            dialog.show(supportFragmentManager, "PeriodOfTimeDialog")
        }

        typeOfTimeLayout.setOnClickListener{
            var dialog = TypeOfTimeDialog()

            dialog.show(supportFragmentManager, "TypeOfTimeDialog")
        }

        doneBtn.setOnClickListener{
            var dialog = DoneInterestDialog()

            dialog.show(supportFragmentManager, "DoneDialog")
        }
    }
}