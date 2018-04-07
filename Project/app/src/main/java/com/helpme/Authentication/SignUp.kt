package com.helpme.Authentication
import Control.UserControl
import android.content.Intent
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import com.helpme.R
import android.app.DatePickerDialog
import android.content.Context
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.*
import kotlinx.android.synthetic.main.activity_sign_up.*

import java.util.*

class SignUp : AppCompatActivity() {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /*                                                                                          //
    By Mohamed                                                                                 //
    */                                                                                        //
    val UserController: UserControl = UserControl.getInstance(null, this) //

    ///////////////////////////////////////////////////////////////////////////////////////////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        birthDatePicker.onFocusChangeListener=object: View.OnFocusChangeListener{
            override fun onFocusChange(p0: View?, p1: Boolean) {
                if(p1){
                    datePicker()
                }
            }
        }
        birthDatePicker.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                datePicker()
            }
        })


        genderSwitch.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if(p1) genderSwitch.setText(genderSwitch.textOn) else genderSwitch.setText(genderSwitch.textOff)
            }
        })
        //startMagic()
    }
    fun btnRegisterClicked(view: View) {
        onTouchEvent(null) // remove keypad
        register.isEnabled = false
        val buttonTimer = Timer()
        buttonTimer.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    register.isEnabled=true
                }
            }
        }, 2000)
        //validaaaaaaaaaaaaation
        //Validation edited to meet mvc concept

        UserController.CreateNewUser()

    }
    // added by mohamed
    fun Signup() {
        val intent = Intent(this, SignIn::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.putExtra("EXIT", true)
        startActivity(intent)
    }
    fun ShowToast(Msg: String) {
        Toast.makeText(this, Msg, Toast.LENGTH_LONG).show();
    }
    fun startMagic() {
        val face = Typeface.createFromAsset(assets, "Fonts/Nabila.ttf");
        val linearLayoutPanel = findViewById<LinearLayout>(R.id.signUpLinearLayout)
        val logo = findViewById<TextView>(R.id.Logo)
        logo.typeface = face;
        linearLayoutPanel.startAnimation(AnimationUtils.loadAnimation(this.applicationContext, R.anim.fade_in))
    }
    fun datePicker(view: View?=null){
        onTouchEvent(null) // remove keypad
        val cal=Calendar.getInstance()
        val year:Int=cal.get(Calendar.YEAR)
        val month:Int=cal.get(Calendar.MONTH)
        val day:Int=cal.get(Calendar.DAY_OF_MONTH)
        var dialog=DatePickerDialog(this,object:DatePickerDialog.OnDateSetListener
        {
            override fun onDateSet(p0: DatePicker?, yyyy: Int, mm: Int, dd: Int) {
                birthDatePicker.setText("$dd/${mm+1}/$yyyy")
            }
        },year,month,day)
        dialog.show()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@SignUp, SignIn::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val view = this.currentFocus
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm!!.hideSoftInputFromWindow(view.windowToken, 0)
        return true
    }
    fun get_userName() = userName.text.toString()
    fun get_eMail() = eMail.text.toString()
    fun get_password()= password.text.toString()
    fun get_passwordconfirm() = passwordconfirm.text.toString()
    fun get_firstName() = firstName.text.toString()
    fun get_midName() = midName.text.toString()
    fun get_lastName() = lastName.text.toString()
    fun get_phoneNumber() = phoneNumber.text.toString()
    fun get_birthDate() = birthDatePicker.text.toString()
    fun get_gender()=genderSwitch.text.toString()
}
