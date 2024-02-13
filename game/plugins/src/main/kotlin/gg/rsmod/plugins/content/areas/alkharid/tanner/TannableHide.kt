package gg.rsmod.plugins.content.areas.alkharid.tanner

data class TannableHide(val id: Int, val product: Int, val name: String, val hidePrice: Int) {
    val price = hidePrice.toString()
}