select identificador_caso, concat(nome_popular_comercial, ', ', substancia_genero_especie, ', ', subclasse_agente, ', ', classe_agente) as nome from "06_agente_intoxicante";

select paciente.idade, COALESCE(paciente.peso, '0') as peso, paciente.sexo, manifestacao.manifestacao_apresentada, manifestacao.classificacao_manifestacao, concat(tratamento.grupo_tratamento, ': ', tratamento.subgrupo_tratamento) as tratamento from "04_paciente" paciente
join "07_manifestacao" manifestacao on paciente.identificador_caso = manifestacao.identificador_caso
join "08_tratamento" tratamento on manifestacao.identificador_caso = tratamento.identificador_caso;


select paciente.idade, COALESCE(paciente.peso, '0') as peso, paciente.sexo, manifestacao.manifestacao_apresentada, manifestacao.classificacao_manifestacao, replace(tratamento.subgrupo_tratamento, ',', '') as tratamento from "04_paciente" paciente
join "07_manifestacao" manifestacao on paciente.identificador_caso = manifestacao.identificador_caso
join "08_tratamento" tratamento on manifestacao.identificador_caso = tratamento.identificador_caso
where tratamento.identificador_caso in (
8, 47, 49, 156, 161, 199, 259, 261, 4447, 4568, 4601, 4719, 4740, 5026, 5293, 5817, 5822, 5843, 6079, 6123, 6230, 6326, 6420, 6655, 6760, 6784, 6814, 6866, 6868, 6876, 7014, 7096, 7285, 7447, 7519, 7521, 7687, 7848, 8084, 8101, 8457, 8580, 8584, 8932, 8936, 8979, 8981, 8982, 8983, 8994, 8998, 9172, 9243, 9276, 9388, 9418, 9477, 9511, 10182, 10427, 10473, 10497, 10604, 10653, 10768, 11070, 11084, 11146, 11333, 11339, 11401, 11603, 11790, 11956, 12137, 12419, 12629, 12747, 12814, 13117, 13901, 14060, 14210, 14227, 14314, 14371, 14685, 14842, 14913, 15312, 15313, 15474, 15700, 16231, 16246, 16266, 16279, 16473, 16503, 16748, 16764, 17021, 17183, 17221, 17491, 17589, 17665, 17741, 18410, 19104, 19352, 19413, 19703, 19792, 19813, 20008, 20182, 20270, 20376, 20587, 20846, 20979, 21139, 21205, 21545, 21716, 21766, 21786, 21962, 22169, 22190, 22268, 22403, 22640, 22794, 22905, 23034, 23183, 23650, 23651, 23739, 23899, 24009, 24035, 24077, 24259, 24288, 24376, 24897, 25565, 25659, 25692, 25865, 25921, 26086, 26189, 26202, 26255, 26529, 26619, 26627, 26643, 26767, 26918, 27204, 27433, 27547, 27595, 27773, 28262, 28540, 28547, 28746, 28824, 29019, 29244, 29270, 29608, 29614, 30101, 30343, 30346, 30753, 31019, 31182, 31378, 31399, 31814, 31896, 32023, 32030, 32068, 32145, 32146, 32191, 32224, 32927, 33165, 33238, 33430, 33519, 34029, 34091, 34151, 34536, 34559, 35045, 35123, 35226, 35445, 35716, 36178, 36236, 36267, 36398, 36500, 36501, 36505, 36604, 36616, 36761, 36878, 37064, 37081, 37383, 37408, 37617, 37668, 37826, 37836, 38219, 38319, 38765, 39028, 39127, 39190, 39330, 39338, 39430, 39621, 39731, 40014, 40549, 40561, 40672, 40773, 40971, 41025, 41148, 41279, 41396, 41459, 41710, 42244, 42358, 42592, 42760, 42976, 43276, 43472, 43704, 43743, 43851, 44251, 44322, 44554, 45057, 45121, 45423, 45468, 46012, 46024, 46089, 46261, 46362, 46368, 46803, 47275, 47356, 47381, 47841, 48339, 48568, 48581, 48695, 48759, 48812, 49428, 49555, 49569, 49668, 49819, 49861, 50074, 50608, 50699, 50710, 50874, 50901, 51050, 51126, 51304, 51518, 51665, 51688, 51780, 51973, 52110, 52121, 52172, 52276, 52344, 52565, 52880, 52892, 53102, 53648, 53731, 53758, 53868, 54316, 54345, 54467, 54699, 54879, 55004, 55005, 55052, 55107, 55160, 55396, 55481, 55546, 55563, 55658, 55931, 56069, 56098, 56290, 56311, 56372, 56387, 56520, 56666, 56690, 56784, 56838, 56947, 57281, 57534, 57936, 57974, 58165, 58455, 58608, 58822, 59114, 59343, 59515, 59657, 59761, 60623, 60915, 60951, 61021, 61165, 61322, 61405, 61475, 61631, 61643, 61646, 61656, 61856, 61937, 62058, 62435, 62728, 63078, 63162, 63260, 63714, 63940, 64041, 64127, 64359, 65281, 65372, 65897, 65980, 66000, 66312, 66627, 66828, 67120, 67513, 67782, 68110, 68348, 68576, 69125, 69140, 69205, 69465, 69469, 69668, 69783, 69969, 70031, 70547, 70642, 70712, 70775, 71041, 71369, 72373, 72459, 72503, 72938, 73514, 73737, 73754, 73786, 73989, 74223, 74526, 75371, 75463, 76033, 76302, 76459, 76509, 76672, 76779, 77063, 77312, 77726, 77855, 78596, 78687, 78876, 79554, 79634, 79649, 79739, 79755, 79935, 80081, 80241, 80647, 81086, 81306, 81657, 82843, 83193, 83559, 85054, 85264, 85743, 86806, 86871, 87343, 87387, 87481, 87788, 88025, 88340, 88376, 88390, 88503, 88505, 88506, 88507, 88508, 88509, 88910, 89078, 89143, 90559, 90819, 90864, 91290, 91373, 91588, 91662, 91678, 91808, 92067, 92288, 92772, 92778, 93092, 93181, 93372, 93991, 94967, 95246, 95559, 95703, 96493, 96816, 96941, 97594, 97641, 98483, 99003, 99051, 99693, 99713, 100300, 100524, 101353, 101619, 101625, 101838, 101906, 102219, 102281, 102640, 102943, 103180, 103184, 103185, 103318, 104539, 104598, 104724, 104944, 105088, 105491, 105781, 105849, 105990, 106054, 106093, 106142, 106241, 106522, 107199, 107353, 108944, 109966, 110205, 110259, 110286, 110414, 110670, 111042, 112008, 112039, 112040, 112041, 112082, 112266, 112359, 112384, 112697, 112729, 112743, 113512, 113908, 114092, 114451, 114522, 114999, 115021, 115152, 115153, 115154, 115155, 115156, 115157, 115158, 115159, 115160, 115161, 115162, 115163, 115164, 115165, 115166, 115167, 115168, 115169, 115170, 115171, 115172, 115173, 115174, 115175, 115176, 115177, 115178, 115179, 115180, 115181, 115325, 115437, 115438, 115708, 116085, 116252, 116684, 116915, 116916, 116917, 116918, 117420, 118162, 118634, 118946, 119566, 119614, 119682, 119965, 119967, 120133, 120618, 121144, 121301, 122377, 122382, 122531, 122601, 122630, 122644, 122834, 122954, 123319, 123652, 123785, 124512, 124513, 124514, 124523, 124524, 124525, 125173, 125213, 125226, 125545, 125726, 125784, 126462, 126555, 127596, 127750, 127876, 128239, 128342, 128663, 128838, 129667, 129817, 129990, 130010, 130091, 130179, 130492, 130815, 131024, 131601, 131799, 131843, 132595, 132845, 132850, 132908, 133383, 133981, 134400, 134481, 134687, 134913, 135019, 135041, 135409, 135427, 136165, 136221, 136595, 136962, 137163, 138427, 138428, 138429, 138430, 138431, 138432, 138433, 138434, 138435, 138436, 138536, 138791, 139004, 139279, 139540, 139713, 140061, 140540, 140807, 140852, 141188, 141191, 141252, 141341, 141402, 141403, 141842, 141972, 142194, 142198, 142453, 142889, 143079, 143218, 143521, 144268, 144277, 144636, 145078, 145100, 145278, 145686, 145783, 145853, 145976, 146371, 146378, 146431, 146543, 147145, 147312, 147473, 147961, 148409, 148411, 148576, 148681, 148932, 148947, 149279, 149410, 149688, 150184, 150185, 150245, 150502, 150547, 151109, 151794, 152257, 152966, 153292, 153365, 153579, 153759, 153936, 153989, 155042, 155050, 155487, 155572, 155707, 155959, 155988, 156328, 156394, 156443, 157275, 157595, 157699, 157841, 157880, 158122, 158192, 158794, 158818, 159154, 159265, 159344, 159667, 159848, 161067, 161253, 161837, 162004, 162049, 162051, 162157, 162281, 163086, 163095, 163250, 164048, 164218, 164231, 164530, 164816, 165618, 165702, 165819, 166140, 166356, 166369, 166580, 166885, 166892, 167199, 167690, 169030, 169657, 169970, 170051, 170492, 170540, 170568, 170782, 171033, 171075, 171264, 171303, 171629, 171671, 172019, 172477, 172698, 172855, 173116, 173322, 173421, 173490, 175598, 175679, 176045, 176158, 176401, 176456, 176747, 176803, 177030, 177606, 178081, 178090, 178143, 178300, 178866, 179017, 179094, 179291, 179292, 179430, 180819, 181412, 182083, 183924, 184329, 185095, 185898, 186272, 186537, 186729, 187355, 188345, 188831, 189133, 189336, 189401, 189689, 190635, 190652, 191738, 192196, 192305, 192735, 192761, 192807, 193017, 193196, 193233, 193939, 194058, 194710, 194880, 195683, 197014, 197087, 197216, 197553, 198556, 198722, 198723, 198774, 198889, 200376, 201150, 201446, 201632, 201715, 202325, 202558, 202559, 204994, 205526, 205731, 207106, 208196, 208260, 208409, 208717, 209096, 209922, 210336, 211219, 211923, 212242, 212266, 212376, 212914, 213030, 213678, 213986, 214129, 216042, 216043, 216149, 216350, 216351, 216352, 217798, 218266, 218620, 219060, 219109, 219180, 219414, 219921, 220363, 220462, 221104, 221209, 221392, 221883, 223554, 223555, 223977, 223978, 223979, 224211, 224957, 225464, 225763, 226022, 226801, 226992, 227214, 229406, 230393, 230635, 230870, 230923, 231220, 231832, 232168, 232291, 232632, 232633, 233256, 233663, 234593, 234628, 234690, 235058, 235191, 235333, 236192, 236267, 236356, 236716, 237736, 237836, 238167, 238303, 238640, 238913, 239179, 239641, 239790, 239803, 240040, 240041, 240442, 241421, 241666, 241739, 241787, 241904, 242016, 242185, 242507, 242516, 242716, 242718, 242719, 242720, 243414, 243477, 243717, 243839, 243854, 244059, 244067, 244374, 244652, 244710, 244940, 245176, 245462, 245574, 245743, 245755, 245838, 246148, 246286, 246304, 247137, 247337, 247406, 247550, 247613, 247619, 247817, 248253, 248261, 248283, 248311, 248428, 248436, 248486, 248722, 248828, 248915, 249135, 249156, 249240, 249276, 249296, 249331, 249332, 249333, 249447, 249564, 250053, 250337, 250777, 250883, 251096, 251451, 251822, 252066, 252538, 252650, 253156, 253159, 253274, 253314, 253744, 253806, 253813, 253823, 254436, 254486, 254696, 255029, 255191, 255267, 255724, 256765, 256767, 256850, 257154, 257494, 257732, 257772, 258747, 258905, 259171, 259237, 259436, 259787, 259845, 260301, 260727, 260748, 260888, 261333, 261521, 261679, 261933, 262058, 262074, 262078, 262108, 262230, 262306, 262446, 262753, 263208, 263339, 263342, 263368, 263404, 263513, 263561, 263713, 263906, 264020, 264028, 264158, 264421, 264423, 264427, 264439, 264443, 264581, 264839, 265112, 265207, 265214, 265427, 265573, 265923, 267108, 267609, 267712, 268532, 268864, 269086, 269242, 269612, 269861, 270259, 270265, 270282, 270400, 270541, 270736, 271018, 271134, 271276, 271354, 271438, 271524, 272391, 272435, 272976, 273218, 273240, 273792, 274066, 274209, 274415, 274700, 275242, 275451, 275667, 275908, 276035, 276239, 276270, 276272, 276591, 276790, 276994, 277352, 277607, 277673, 278286, 278422, 278780, 278872, 279303, 279524, 279772, 280212, 280261, 280263, 281108, 281120, 281189, 281271, 281614, 282222, 282643, 282728, 282921, 283463, 283486, 283495, 283838, 283895, 283959, 285073, 285445, 285653, 286382, 286524, 286533, 286682, 287072, 287102, 287518, 287988, 288225, 288262, 288443, 288665, 288845, 289991, 290286, 290358, 291050, 291334, 291512, 291633, 291634, 291635, 291636, 291772, 291815, 291868, 292027, 292529, 293065, 293077, 295141, 295540, 295723, 295728, 296129, 296213, 296258, 296345, 296717, 298175, 298252, 298516, 299124, 299369, 300304, 300364, 300574, 300896, 301736, 303770, 304941, 306347, 306393, 307892, 307905, 308390, 308530, 308686, 308781, 309308, 309343, 309446, 309964, 310185, 310856, 311697, 311917, 311996, 312367, 313418, 313827, 314207, 314590, 316985, 317114, 317200, 317445, 318514, 318735, 318796, 318841, 320530, 322754, 323241, 323668, 323840, 324291, 324750, 325234, 325469, 325941, 326384, 327340, 327389, 327517, 328229, 328477, 328665, 328789, 328962, 329794, 329989, 330573, 331485, 331728, 332114, 332116, 332117, 334640, 334648, 334668, 335058, 335799, 336293, 336742, 337564, 338201, 338377, 338801, 338982, 340165, 340280, 341407, 341449, 341513, 342806, 343380, 343685, 343697, 344274, 344275, 344276, 344374, 344593, 344610, 344764, 345294, 345339, 345915, 346071, 348120, 348310, 349367, 349752, 349874, 349985, 351636, 352023, 352393, 353258, 353791, 354123, 354165, 354286, 354356, 354512, 355529, 355631, 355835, 355848, 355877, 355971, 356398, 356768, 356770, 356906, 357128, 357947, 358275, 358925, 359399, 360151, 360239, 360649, 361366, 361584, 361887, 362568, 362737, 362847, 362911, 363402, 363729, 363758, 363763, 364238, 364253, 364439, 364807, 365020, 365059, 365069, 365222, 365883, 365894, 365895, 365896, 366000, 366400, 366403, 366450
)
and tratamento.subgrupo_tratamento not like '%Sintomático e Suportivo%' and tratamento.subgrupo_tratamento not like '%Observar sinais e sintomas%';