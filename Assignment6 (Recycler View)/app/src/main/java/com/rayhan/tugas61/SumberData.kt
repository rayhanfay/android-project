package com.rayhan.tugas61

class SumberData {
    companion object {
        fun buatSetData(): ArrayList<ListObjDosen> {
            val list = ArrayList<ListObjDosen>()
            //list dosen disimpan disini, dan akan ditampilkan pada aplikasi

            list.add(
                ListObjDosen(
                    "Dr. Ananda, S.Kom., M.Kom.",
                    "Digital Image Processing, Software Engineering",
                    "https://pcr.ac.id/assets/images/pegawai/AND20240906033830.jpg",
                    "198502252008121001",
                    "Rekayasa Perangkat Lunak",
                    "R.304"
                )
            )

            list.add(
                ListObjDosen(
                    "Kartina Diah Kesuma Wardhani, S.T., M.T.",
                    "Data Engineering, Data Mining, Data Science, Artificial Intelligence, Machine Learning, Deep Learning",
                    "https://pcr.ac.id/assets/images/pegawai/DYH20240910022824.jpg",
                    "198709252015042002",
                    "Machine Learning",
                    "R.201"
                )
            )

            list.add(
                ListObjDosen(
                    "Silvana Rasio Henim, S.ST., M.T.",
                    "Programming, Mobile Development",
                    "https://pcr.ac.id/assets/images/pegawai/SRH20210923114925.JPG",
                    "198607122010122003",
                    "Pemrograman Mobile",
                    "R.202"
                )
            )

            list.add(
                ListObjDosen(
                    "Agus Urip Ari Wibowo, S.T., M.T.",
                    "Internet of Things, Embedded Systems",
                    "https://pcr.ac.id/assets/images/pegawai/AUA20240909074934.jpg",
                    "197808152009121004",
                    "Internet of Things",
                    "R.303"
                )
            )

            list.add(
                ListObjDosen(
                    "Ibnu Surya, S.T., M.T.",
                    "Operating System, Computer Architecture",
                    "https://pcr.ac.id/assets/images/pegawai/ISA20240910023450.jpg",
                    "198803122012081005",
                    "Sistem Operasi",
                    "R.301"
                )
            )

            list.add(
                ListObjDosen(
                    "Rika Perdana Sari, S.T., M.Eng.",
                    "Machine Learning, Artificial Intelligence",
                    "https://pcr.ac.id/assets/images/pegawai/RPS20240229082251.jpg",
                    "199002182011072006",
                    "Kecerdasan Buatan",
                    "R.203"
                )
            )

            list.add(
                ListObjDosen(
                    "Muhammad Ihsan Zul, S.Pd., M.Eng.",
                    "Database, ICT Project Management, Software Engineering, Machine Learning",
                    "https://pcr.ac.id/assets/images/pegawai/MIZ20240910025353.jpg",
                    "198607152010121007",
                    "Basis Data Lanjut",
                    "R.204"
                )
            )

            list.add(
                ListObjDosen(
                    "Maksum Ro'is Adin Saf, S.Kom., M.Eng.",
                    "ITC Project Management, Software Engineering",
                    "https://pcr.ac.id/assets/images/pegawai/MRA20240910030119.jpg",
                    "198910102013081008",
                    "Manajemen Proyek IT",
                    "R.302"
                )
            )

            return list
        }
    }
}