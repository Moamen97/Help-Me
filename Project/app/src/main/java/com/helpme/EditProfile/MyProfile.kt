package com.helpme.EditProfile

import Common.mySelf
import Control.UserControl
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.View
import android.widget.*
import com.helpme.R
import com.helpme.UploadImages.UploadProfileImage
import kotlinx.android.synthetic.main.activity_my_profile.*
import java.util.*

class MyProfile : AppCompatActivity() {
    val UserController: UserControl = UserControl.getInstance(null, null, null, this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)
        userImage.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent = Intent(this@MyProfile, UploadProfileImage::class.java)
                startActivity(intent)
                finish()
            }
        })

        val firstname = mySelf.get_firstName()
        val midname = mySelf.get_midName()
        val lastname = mySelf.get_lastName()
        val n_of_posts = mySelf.myPosts.size.toString()
        val rate = mySelf.get_behaveRate().toString()

        // imageAbb.text = firstname[0].toUpperCase().toString()
        personName.text = "Name : " + firstname + " " + midname + " " + lastname
        personNoOfPosts.text = n_of_posts
        personRate.text = rate
        var bm = mySelf.get_image()
        if (bm != null)
            userImage.setImageBitmap(bm)

        val editMyProfile = findViewById<FloatingActionButton>(R.id.fab)
        val dialog: Dialog = Dialog(this)
        dialog.setContentView(R.layout.edit_profile_fragment)
        editMyProfile.setOnClickListener {
            var FirstName: EditText = (dialog.findViewById<(EditText)>(R.id.editFirstNametextbox))
            var MidName: EditText = (dialog.findViewById<(EditText)>(R.id.editMidNametextbox))
            var LastName: EditText = (dialog.findViewById<(EditText)>(R.id.editLastNametextbox))
            var Gender: Switch = (dialog.findViewById<(Switch)>(R.id.genderSwitch2))
            var email: EditText = (dialog.findViewById<(EditText)>(R.id.editEmailtextbox))
            var password: EditText = (dialog.findViewById<(EditText)>(R.id.editPassWordtextbox))
            var mobile: EditText = (dialog.findViewById<(EditText)>(R.id.editPhonetextbox))
            var BDate: AutoCompleteTextView = (dialog.findViewById<(AutoCompleteTextView)>(R.id.editBirthDatetextbox))

            FirstName.setText(mySelf.get_firstName())
            MidName.setText(mySelf.get_midName())
            LastName.setText(mySelf.get_lastName())
            if (mySelf.get_gender() == "female") {
                Gender.isChecked = true
            }
            email.setText(mySelf.get_email())
            mobile.setText(mySelf.get_phoneNum())
            BDate.setText(mySelf.get_birthDate())

            val editBirthDatetextbox: AutoCompleteTextView = dialog.findViewById(R.id.editBirthDatetextbox)
            editBirthDatetextbox.onFocusChangeListener = object : View.OnFocusChangeListener {
                override fun onFocusChange(p0: View?, p1: Boolean) {
                    if (p1) {
                        datePicker(dialog)
                    }
                }
            }
            editBirthDatetextbox.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    datePicker(dialog)
                }
            })

            Gender.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                    if (p1) Gender.text = Gender.textOn else Gender.text = Gender.textOff
                }
            })

            val buttonSave = dialog.findViewById<Button>(R.id.SaveInfo)
            dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            buttonSave.setOnClickListener {
                editMyProfile(dialog)
            }
        }

    }

    fun btnAddWorkShops(view: View) {
        var intent = Intent(this, AddWorkShop::class.java)
        startActivity(intent)
    }

    fun getmyposts(view: View) {
        var intent = Intent(this, AddWorkShop::class.java)
        startActivity(intent)
    }

    fun editMyProfile(dialog: Dialog) {
        var newFirstName: String = (dialog.findViewById<(EditText)>(R.id.editFirstNametextbox)).text.toString();
        var newMidName: String = (dialog.findViewById<(EditText)>(R.id.editMidNametextbox)).text.toString();
        var newLastName: String = (dialog.findViewById<(EditText)>(R.id.editLastNametextbox)).text.toString();
        var Gender: Switch = (dialog.findViewById<(Switch)>(R.id.genderSwitch2))

        var newGender = ""
        if (Gender.text == "male")
            newGender = "male"
        else newGender = "female"

        var newemail: String = (dialog.findViewById<(EditText)>(R.id.editEmailtextbox)).text.toString();
        var newpassword: String = (dialog.findViewById<(EditText)>(R.id.editPassWordtextbox)).text.toString();
        var newmobile: String = (dialog.findViewById<(EditText)>(R.id.editPhonetextbox)).text.toString();
        var newimageid = ""
        var newBirthDate: String = (dialog.findViewById<(EditText)>(R.id.editBirthDatetextbox)).text.toString();

        UserController.checkBeforUpdate(newFirstName, newMidName, newLastName, newGender
                , newemail, newpassword, newmobile, newBirthDate)
        dialog.dismiss()
    }

    fun updateinfoviewer() {
        val intent = intent
        this.finish()
        startActivity(intent)
    }

    fun ShowToast(Msg: String) {
        Toast.makeText(this, Msg, Toast.LENGTH_LONG).show();
    }

    fun datePicker(dialog: Dialog, view: View? = null) {
        onTouchEvent(null) // remove keypad
        val cal = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)
        var dialog1 = DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(p0: DatePicker?, yyyy: Int, mm: Int, dd: Int) {
                val editBirthDatetextbox: AutoCompleteTextView = dialog.findViewById(R.id.editBirthDatetextbox)
                editBirthDatetextbox.setText("$dd/${mm + 1}/$yyyy")
            }
        }, year, month, day)
        dialog1.show()
    }

}
