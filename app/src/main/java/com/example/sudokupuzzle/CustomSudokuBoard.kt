package com.example.sudokupuzzle

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.ceil

class CustomSudokuBoard(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var boardColor: Int = Color.BLACK
    private var cellFocusedColor: Int = Color.BLACK
    private var cellHighlightColor: Int = Color.BLACK
    private var letterColor: Int = Color.BLACK
    private var letterSolveColor: Int = Color.BLACK
    private var boardColorPaint: Paint
    private var cellFocusedColorPaint: Paint
    private var cellHighlightColorPaint: Paint
    private lateinit var letterPaint: Paint
    private lateinit var letterPaintBounds: Rect
    private var sudokuSolver: SudokuSolver = SudokuSolver()
    private  var cellSize: Float = 0F

    init {
        val typedArray = context!!.theme.obtainStyledAttributes(
            attrs, R.styleable.CustomSudokuBoard, 0, 0
        )

        boardColor = typedArray.getInteger(R.styleable.CustomSudokuBoard_boardColor, 0)
        cellFocusedColor = typedArray.getInteger(R.styleable.CustomSudokuBoard_cellFocusedColor, 0)
        cellHighlightColor = typedArray.getInteger(R.styleable.CustomSudokuBoard_cellHighlightColor, 0)
        letterColor = typedArray.getInteger(R.styleable.CustomSudokuBoard_letterColor, 0)
        letterSolveColor = typedArray.getInteger(R.styleable.CustomSudokuBoard_letterSolveColor, 0)

        typedArray.recycle()

        boardColorPaint = Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = STROKE_WIDTH
            color = boardColor
            isAntiAlias = true
        }

        cellFocusedColorPaint = Paint().apply {
            style = Paint.Style.FILL
            color = cellFocusedColor
            isAntiAlias = true
        }

        cellHighlightColorPaint = Paint().apply {
            style = Paint.Style.FILL
            color = cellHighlightColor
            isAntiAlias = true
        }
    }

    override fun onMeasure(width: Int, height: Int) {
        super.onMeasure(width, height)
        val dimension = measuredWidth.coerceAtMost(measuredHeight)
        setMeasuredDimension(dimension,dimension)
        cellSize = (dimension/9).toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        if (canvas == null) {
            return
        }
        sudokuSolver?.let {
            colorCell(canvas, it.selectedRow, it.selectedColumn)
        }

        canvas.drawRect(TOP_LEFT_CORNER,
                        BOTTOM_RIGHT_CORNER,
                        width.toFloat(),
                        height.toFloat(),
                        boardColorPaint)
        drawBoard(canvas)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val isValid : Boolean
        val x  = event?.x
        val y  = event?.y
        val action = event?.action
        if (action == MotionEvent.ACTION_DOWN){
            isValid = true
            if (y != null) {
                sudokuSolver?.selectedRow = ceil(y / cellSize).toInt()
            }
            if (x != null) {
                sudokuSolver?.selectedColumn = ceil(x / cellSize).toInt()
            }
        }else{
            isValid = false
        }
        return isValid
    }

    private fun drawNumbers(canvas: Canvas){
        for (r in 0..8){
            for (c in 0..8){
                if (sudokuSolver.board[r][c] != 0){
                    val text = sudokuSolver.board[r][c].toString()
                    letterPaint.getTextBounds(text,0,text.length,letterPaintBounds)
                    var width = letterPaint.measureText(text)
                    var height = letterPaintBounds.height()
                    canvas.drawText(text,(c*cellSize)+((cellSize - width))/2,
                        (r*cellSize+cellSize) - ((cellSize-height)/2),letterPaint)
                }
            }
        }

    }

    private fun colorCell(canvas: Canvas, row: Int, col: Int){
        canvas.drawRect((col-1)*cellSize,0F, col*cellSize, cellSize*9, cellHighlightColorPaint)
        canvas.drawRect(0F,(row-1)*cellSize, cellSize*9, row*cellSize, cellHighlightColorPaint)
        canvas.drawRect((col-1)*cellSize,(row-1)*cellSize, col*cellSize, cellSize*9, cellHighlightColorPaint)
        invalidate()
    }

    private fun drawThickLine(){
        boardColorPaint = Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = STROKE_THICK
            color = boardColor
        }
    }

    private fun drawThinLine(){
        boardColorPaint = Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = STROKE_THIN
            color = boardColor
        }
    }

    private fun drawBoard(canvas: Canvas){
        for (row in 0..8){
            if (row % 3 == 0){
                drawThickLine()
            }else{
                drawThinLine()
            }
            canvas.drawLine(0F,
                     cellSize*row,
                           width.toFloat() ,
                    cellSize*row,
                         boardColorPaint)
        }
        for (col in 0..8){
            if (col % 3 == 0){
                drawThickLine()
            }else{
                drawThinLine()
            }
            canvas.drawLine(cellSize*col,
                0F,
                cellSize*col,
                width.toFloat(),
                boardColorPaint)
        }
    }


  companion object{
      private const val STROKE_WIDTH = 16F
      private const val STROKE_THICK = 10F
      private const val STROKE_THIN = 5F
      private const val TOP_LEFT_CORNER = 0F
      private const val BOTTOM_RIGHT_CORNER = 0F
  }


}