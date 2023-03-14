package com.example.sudokupuzzle

class SudokuSolver {
     var selectedRow: Int = -1
     var selectedColumn: Int = -1
     var board = Array(9) { IntArray(9) }
     var emptyBoxIndex = ArrayList<ArrayList<Any>>()

     init {
         for (r in 0..8){
              for (c in 0..8){
                   board[r][c] = 0
              }
         }
     }

     private fun getEmptyBoxIndices(){
          for (r in 0..8){
               for (c in 0..8){
                    if (board[r][c] == 0){
                         emptyBoxIndex.add(java.util.ArrayList())
                         emptyBoxIndex[emptyBoxIndex.size-1].add(r)
                         emptyBoxIndex[emptyBoxIndex.size-1].add(c)
                    }
               }
          }
     }

     private fun setNumberPosition(number: Int){
          if (selectedRow != -1 && selectedColumn != -1){
               if (board[selectedRow][selectedColumn] == number){
                    board[selectedRow][selectedColumn] = 0
               }else{
                    board[selectedRow][selectedColumn] = number
               }
          }
     }
}