package co.tiagoaguiar.ganheinamega

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var bd: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val editText: EditText = findViewById(R.id.edit_number)
        val textResult: TextView = findViewById(R.id.txt_result)
        val btnGenerate: Button = findViewById(R.id.btn_generate)

        val string: String = getString(R.string.last_bet)

        bd = getSharedPreferences("db_megasena", Context.MODE_PRIVATE)
        val cache = bd.getString("result", null)
        cache?.let {
            textResult.text = "$string $it"
        }


        btnGenerate.setOnClickListener {

            val input = editText.text.toString()

            numberGenerator(input, textResult)
        }

    }

    private fun numberGenerator(input: String, output: TextView) {
        val msg: String = getString(R.string.message)

        if (input.isEmpty()) {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
            return
        }

        val amount = input.toInt()
        if (amount < 6 || amount > 15) {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
            return
        }

        val numbers = mutableSetOf<Int>()
        val random = Random()

        while (true) {
            val number = random.nextInt(60)
            numbers.add(number + 1)

            if (numbers.size == amount) {
                break
            }
        }
        output.text = numbers.joinToString(" | ")

        val editor = bd.edit()
        editor.putString("result", output.text.toString())
        editor.apply()
    }
}