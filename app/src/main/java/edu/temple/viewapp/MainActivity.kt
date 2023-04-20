package edu.temple.viewapp

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    val magReadings = FloatArray(3)
    val accelReadings = FloatArray(3)

    val matrix = FloatArray(9)
    val rotation = FloatArray(3)

    val sensorManager: SensorManager by lazy {
        getSystemService(SensorManager::class.java)
    }

    val accelSensor: Sensor by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    val magSensor: Sensor by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }

    val sensorEventListener : SensorEventListener by lazy {
        object: SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {

                event?.run {
                    if (sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
                        System.arraycopy(values, 0, magReadings, 0, magReadings.size)
                    } else {
                        System.arraycopy(values, 0, accelReadings, 0, accelReadings.size)
                    }

                    SensorManager.getRotationMatrix(matrix, null, accelReadings, magReadings)

                    SensorManager.getOrientation(matrix, rotation)

                    myView.rotationChanged(rotation)

                }

            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            }

        }
    }

    val myView: MyView by lazy {
        MyView(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(myView)

    }

    override fun onResume() {
        super.onResume()

        sensorManager.registerListener(sensorEventListener, accelSensor, SensorManager.SENSOR_DELAY_UI)
        sensorManager.registerListener(sensorEventListener, magSensor, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorEventListener)
    }
}