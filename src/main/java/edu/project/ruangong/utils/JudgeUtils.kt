package edu.project.ruangong.utils

import edu.project.ruangong.enums.applyenum
import edu.project.ruangong.enums.isjudgeEnum

/**
 * @author  athonyw
 * @date  2019/11/27 1:31 下午
 * @version init
 */
class JudgeUtils {

    companion object{
        fun getbycode(code:Int?):String?{
            var res:String?=null
            isjudgeEnum.values().forEach {
                if(code==it.code){
                    res = it.msg.toString()
//                    println(it.code)
                }
            }
            return res
        }

        fun gettypebycode(code:Int?):String?{
            var res:String?=null
            applyenum.values().forEach {
                if(code==it.code){
                    res = it.msg.toString()
//                    println(it.code)
                }
            }
            return res
        }
    }
}

fun main() {
    println(JudgeUtils.getbycode(0))
    println(JudgeUtils.gettypebycode(1))
}