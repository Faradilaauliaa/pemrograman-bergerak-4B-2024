//defined class mobil
class Mobil(val merek: String, val tahun: Int, var status: String, val harga: Int) {
    var totalDibayar: Int = 0

    fun sewa(jumlahBayar: Int) {
        if (status == "Tersedia") {
            if (jumlahBayar >= harga) {
                val kembalian = jumlahBayar - harga
                if (kembalian > 0) {
                    status = "Tersewa"
                    totalDibayar = jumlahBayar
                    println("Mobil $merek tahun $tahun berhasil disewa dengan harga $harga, total dibayar: $totalDibayar. Kembalian: $kembalian")
                } else {
                    println("Mobil $merek tahun $tahun berhasil disewa dengan harga $harga, total dibayar: $jumlahBayar.")
                }
            } else {
                println("Jumlah pembayaran tidak mencukupi untuk menyewa mobil.")
            }
        } else {
            println("Mobil $merek tahun $tahun tidak tersedia untuk disewa.")
        }
    }

    fun kembali() {
        if (status == "Tersewa") {
            status = "Tersedia"
            totalDibayar = 0
            println("Mobil $merek tahun $tahun telah dikembalikan.")
        } else {
            println("Mobil $merek tahun $tahun tidak sedang disewa.")
        }
    }
}

fun main() {
    val daftarMobil = mutableListOf(
        Mobil("Daihatsu Ayla", 2015, "Tersedia", 150000),
        Mobil("Honda Civic", 2023, "Tersedia", 200000),
        Mobil("Toyota Kijang Innova", 2019, "Tersedia", 180000)
    )

    // Penyewaan dan pengembalian mobil
    var pilihan: Int
    do {
        println("\nMenu:")
        println("1. Sewa mobil")
        println("2. Kembalikan mobil")
        println("3. Keluar")
        print("Pilihan Anda: ")
        pilihan = readLine()!!.toInt()

        when (pilihan) {
            1 -> {
                println("\nDaftar Mobil Tersedia:")
                daftarMobil.filter { it.status == "Tersedia" }.forEachIndexed { index, mobil ->
                    println("${index + 1}. ${mobil.merek} tahun ${mobil.tahun} (Harga: ${mobil.harga})")
                }
                print("Pilih mobil yang ingin disewa (nomor): ")
                val indexSewa = readLine()!!.toInt() - 1
                if (indexSewa in 0 until daftarMobil.size && daftarMobil[indexSewa].status == "Tersedia") {
                    print("Masukkan jumlah pembayaran: ")
                    val jumlahBayar = readLine()!!.toInt()
                    daftarMobil[indexSewa].sewa(jumlahBayar)
                } else {
                    println("Nomor mobil tidak valid atau mobil tidak tersedia.")
                }
            }
            2 -> {
                println("\nDaftar Mobil Tersewa:")
                daftarMobil.filter { it.status == "Tersewa" }.forEachIndexed { index, mobil ->
                    println("${index + 1}. ${mobil.merek} tahun ${mobil.tahun}")
                }
                print("Pilih mobil yang ingin dikembalikan (nomor): ")
                val indexKembali = readLine()!!.toInt() - 1
                if (indexKembali in 0 until daftarMobil.size && daftarMobil[indexKembali].status == "Tersewa") {
                    daftarMobil[indexKembali].kembali()
                } else {
                    println("Nomor mobil tidak valid atau mobil tidak sedang disewa.")
                }
            }
            3 -> println("Terima kasih!")
            else -> println("Pilihan tidak valid. Silakan pilih lagi.")
        }
    } while (pilihan != 3)
}
