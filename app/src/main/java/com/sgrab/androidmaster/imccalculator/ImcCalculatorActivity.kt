package com.sgrab.androidmaster.imccalculator
import android.content.Intent
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider
import com.sgrab.androidmaster.R
import org.w3c.dom.Text
class ImcCalculatorActivity : AppCompatActivity() {

    //Variables de clase
    private var isMaleSelected: Boolean = true
    private var isFemaleSelected: Boolean = false
    private var currenWeight: Int = 70
    private var currenAge: Int = 20
    private var currenHeight: Int = 120

    // Componentes de la UI (se inicializan en initComponent())
    //lateinit indica que estas variables se inicializarán después (en initComponent()).
    //Cada variable está vinculada a un elemento del XML (CardView, TextView, Button, etc.).
    private lateinit var viewMale: CardView
    private lateinit var viewFemale: CardView
    private lateinit var tvHeight: TextView
    private lateinit var rsHeight: RangeSlider
    private lateinit var btnSubstractWeight: FloatingActionButton
    private lateinit var btnPlusWeight: FloatingActionButton
    private lateinit var tvWeight: TextView
    private lateinit var btnSubstractAge: FloatingActionButton
    private lateinit var btnPlusAge: FloatingActionButton
    private lateinit var tvAge: TextView
    private lateinit var btnCalculate: Button

    //son constantes a la cual todos tienen acceso(Constantes Globales)
    companion object {
        const val IMC_KEY = "IMC_RESULT"
    }

    //Método onCreate
    //onCreate() es el primer método que se ejecuta al abrir la actividad.
    //setContentView() carga el layout XML de la calculadora.
    //initComponent(), initListener() e initUi() organizan el código para mejor legibilidad.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc_calculator)  // Establece el layout
        initComponent()  // Inicializa componentes
        initListener()  // Configura listeners
        initUi()  // Inicializa UI
    }

    //Métodos principales
    //initComponent()
    //Aquí se conectan las variables de Kotlin con los elementos del XML.
    //findViewById() busca cada componente por su id.
    private fun initComponent() {
        viewMale = findViewById<CardView>(R.id.viewMale)
        viewFemale = findViewById<CardView>(R.id.viewFemale)
        tvHeight = findViewById<TextView>(R.id.tvHeight)
        rsHeight = findViewById<RangeSlider>(R.id.rsHeight)
        btnSubstractWeight = findViewById<FloatingActionButton>(R.id.btnSubtractWeight)
        btnPlusWeight = findViewById<FloatingActionButton>(R.id.btnPlusWeight)
        tvWeight = findViewById<TextView>(R.id.tvWeight)
        btnSubstractAge = findViewById<FloatingActionButton>(R.id.btnSubtractAge)
        btnPlusAge = findViewById<FloatingActionButton>(R.id.btnPlusAge)
        tvAge = findViewById<TextView>(R.id.tvAge)
        btnCalculate = findViewById<Button>(R.id.btnCalculate)
    }

    //Métodos principales
    //initListener()
    private fun initListener() {
        viewMale.setOnClickListener {
            isMaleSelected = true
            isFemaleSelected = false
            setGenderColor()
        }

        viewFemale.setOnClickListener {
            isFemaleSelected = true
            isMaleSelected = false
            setGenderColor()
        }

        rsHeight.addOnChangeListener { _, value, _ ->
            val df = DecimalFormat("#.##")
            currenHeight = df.format(value).toInt()
            tvHeight.text = "$currenHeight cm"
        }

        btnPlusWeight.setOnClickListener {
            currenWeight += 1
            setWeight()
        }
        btnSubstractWeight.setOnClickListener {
            currenWeight -= 1
            setWeight()
        }

        btnPlusAge.setOnClickListener {
            currenAge += 1
            setAge()
        }
        btnSubstractAge.setOnClickListener {
            currenAge -= 1
            setAge()
        }

        btnCalculate.setOnClickListener {
            val result = calculateIMC()
            navigateToResult(result)
        }

    }

    //Métodos auxiliares
    private fun navigateToResult(result: Double) {
        val intent = Intent(this, ResultIMCActivity::class.java)
        intent.putExtra(IMC_KEY, result)
        startActivity(intent)
    }

    private fun calculateIMC(): Double {
        val df = DecimalFormat("#,##")
        val imc = currenWeight / (currenHeight.toDouble() / 100 * currenHeight.toDouble() / 100)
        return df.format(imc).toDouble()

    }

    private fun setAge() {
        tvAge.text = currenAge.toString()
    }

    private fun setWeight() {
        tvWeight.text = currenWeight.toString()
    }

    private fun setGenderColor() {
        viewMale.setCardBackgroundColor(getBackgroundColor(isMaleSelected))
        viewFemale.setCardBackgroundColor(getBackgroundColor(isFemaleSelected))
    }

    private fun getBackgroundColor(isSelectedComponent: Boolean): Int {
        val colorReference = if (isSelectedComponent) {
            R.color.background_component_selected
        } else {
            R.color.background_component
        }

        return ContextCompat.getColor(this, colorReference)
    }

    //Métodos principales
    //initUi()(Inicialización de la UI)
    //Este método se llama al inicio para mostrar los valores por defecto.
    private fun initUi() {
        setGenderColor()
        setWeight()
        setAge()
    }
}
