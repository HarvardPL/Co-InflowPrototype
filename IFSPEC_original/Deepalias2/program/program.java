import lbs.harvard.coinflow.CoInFlowUserAPI;
import lbs.harvard.coinflow.internal.Labeled;

class program {
      public static class A {
        private boolean x;
        public A(boolean x) {
        		this.x = x;
        	}
        public A(Labeled<Boolean> x) {
        		CoInFlowUserAPI.raiseObjLabel(this, CoInFlowUserAPI.labelOf(x));
    			this.x = CoInFlowUserAPI.unlabel(x);
        }
        
//        public boolean get() {return (this.x);}
        public Labeled<Boolean> get() {return CoInFlowUserAPI.labelData(x, CoInFlowUserAPI.objLabelOf(this));}
      }
      public static boolean foo(boolean h) {
    	  	A a1 = new A(h);
        A a2 = new A(true);

        A a3 = new A(a1.get());
        A a4 = new A(a2.get());

        A a5 = new A(a3.get());
        A a6 = new A(a4.get());

        A a7 = new A(a5.get());
        A a8 = new A(a6.get());

        A a9 = new A(a7.get());
        A a10 = new A(a8.get());

        A a11 = new A(a9.get());
        A a12 = new A(a10.get());

        A a13 = new A(a11.get());
        A a14 = new A(a12.get());

        A a15 = new A(a13.get());
        A a16 = new A(a14.get());

        A a17 = new A(a15.get());
        A a18 = new A(a16.get());

        A a19 = new A(a17.get());
        A a20 = new A(a18.get());

        A a21 = new A(a19.get());
        A a22 = new A(a20.get());

        A a23 = new A(a21.get());
        A a24 = new A(a22.get());

        A a25 = new A(a23.get());
        A a26 = new A(a24.get());

        A a27 = new A(a25.get());
        A a28 = new A(a26.get());

        A a29 = new A(a27.get());
        A a30 = new A(a28.get());

        A a31 = new A(a29.get());
        A a32 = new A(a30.get());

        A a33 = new A(a31.get());
        A a34 = new A(a32.get());

        A a35 = new A(a33.get());
        A a36 = new A(a34.get());

        A a37 = new A(a35.get());
        A a38 = new A(a36.get());

        A a39 = new A(a37.get());
        A a40 = new A(a38.get());

        A a41 = new A(a39.get());
        A a42 = new A(a40.get());

        A a43 = new A(a41.get());
        A a44 = new A(a42.get());

        A a45 = new A(a43.get());
        A a46 = new A(a44.get());

        A a47 = new A(a45.get());
        A a48 = new A(a46.get());

        A a49 = new A(a47.get());
        A a50 = new A(a48.get());

        A a51 = new A(a49.get());
        A a52 = new A(a50.get());

        A a53 = new A(a51.get());
        A a54 = new A(a52.get());

        A a55 = new A(a53.get());
        A a56 = new A(a54.get());

        A a57 = new A(a55.get());
        A a58 = new A(a56.get());

        A a59 = new A(a57.get());
        A a60 = new A(a58.get());

        A a61 = new A(a59.get());
        A a62 = new A(a60.get());

        A a63 = new A(a61.get());
        A a64 = new A(a62.get());

        A a65 = new A(a63.get());
        A a66 = new A(a64.get());

        A a67 = new A(a65.get());
        A a68 = new A(a66.get());

        A a69 = new A(a67.get());
        A a70 = new A(a68.get());

        A a71 = new A(a69.get());
        A a72 = new A(a70.get());

        A a73 = new A(a71.get());
        A a74 = new A(a72.get());

        A a75 = new A(a73.get());
        A a76 = new A(a74.get());

        A a77 = new A(a75.get());
        A a78 = new A(a76.get());

        A a79 = new A(a77.get());
        A a80 = new A(a78.get());

        A a81 = new A(a79.get());
        A a82 = new A(a80.get());

        A a83 = new A(a81.get());
        A a84 = new A(a82.get());

        A a85 = new A(a83.get());
        A a86 = new A(a84.get());

        A a87 = new A(a85.get());
        A a88 = new A(a86.get());

        A a89 = new A(a87.get());
        A a90 = new A(a88.get());

        A a91 = new A(a89.get());
        A a92 = new A(a90.get());

        A a93 = new A(a91.get());
        A a94 = new A(a92.get());

        A a95 = new A(a93.get());
        A a96 = new A(a94.get());

        A a97 = new A(a95.get());
        A a98 = new A(a96.get());

        A a99 = new A(a97.get());
        A a100 = new A(a98.get());

        A a101 = new A(a99.get());
        A a102 = new A(a100.get());

        A a103 = new A(a101.get());
        A a104 = new A(a102.get());

        A a105 = new A(a103.get());
        A a106 = new A(a104.get());

        A a107 = new A(a105.get());
        A a108 = new A(a106.get());

        A a109 = new A(a107.get());
        A a110 = new A(a108.get());

        A a111 = new A(a109.get());
        A a112 = new A(a110.get());

        A a113 = new A(a111.get());
        A a114 = new A(a112.get());

        A a115 = new A(a113.get());
        A a116 = new A(a114.get());

        A a117 = new A(a115.get());
        A a118 = new A(a116.get());

        A a119 = new A(a117.get());
        A a120 = new A(a118.get());

        A a121 = new A(a119.get());
        A a122 = new A(a120.get());

        A a123 = new A(a121.get());
        A a124 = new A(a122.get());

        A a125 = new A(a123.get());
        A a126 = new A(a124.get());

        A a127 = new A(a125.get());
        A a128 = new A(a126.get());

        A a129 = new A(a127.get());
        A a130 = new A(a128.get());

        A a131 = new A(a129.get());
        A a132 = new A(a130.get());

        A a133 = new A(a131.get());
        A a134 = new A(a132.get());

        A a135 = new A(a133.get());
        A a136 = new A(a134.get());

        A a137 = new A(a135.get());
        A a138 = new A(a136.get());

        A a139 = new A(a137.get());
        A a140 = new A(a138.get());

        A a141 = new A(a139.get());
        A a142 = new A(a140.get());

        A a143 = new A(a141.get());
        A a144 = new A(a142.get());

        A a145 = new A(a143.get());
        A a146 = new A(a144.get());

        A a147 = new A(a145.get());
        A a148 = new A(a146.get());

        A a149 = new A(a147.get());
        A a150 = new A(a148.get());

        A a151 = new A(a149.get());
        A a152 = new A(a150.get());

        A a153 = new A(a151.get());
        A a154 = new A(a152.get());

        A a155 = new A(a153.get());
        A a156 = new A(a154.get());

        A a157 = new A(a155.get());
        A a158 = new A(a156.get());

        A a159 = new A(a157.get());
        A a160 = new A(a158.get());

        A a161 = new A(a159.get());
        A a162 = new A(a160.get());

        A a163 = new A(a161.get());
        A a164 = new A(a162.get());

        A a165 = new A(a163.get());
        A a166 = new A(a164.get());

        A a167 = new A(a165.get());
        A a168 = new A(a166.get());

        A a169 = new A(a167.get());
        A a170 = new A(a168.get());

        A a171 = new A(a169.get());
        A a172 = new A(a170.get());

        A a173 = new A(a171.get());
        A a174 = new A(a172.get());

        A a175 = new A(a173.get());
        A a176 = new A(a174.get());

        A a177 = new A(a175.get());
        A a178 = new A(a176.get());

        A a179 = new A(a177.get());
        A a180 = new A(a178.get());

        A a181 = new A(a179.get());
        A a182 = new A(a180.get());

        A a183 = new A(a181.get());
        A a184 = new A(a182.get());

        A a185 = new A(a183.get());
        A a186 = new A(a184.get());

        A a187 = new A(a185.get());
        A a188 = new A(a186.get());

        A a189 = new A(a187.get());
        A a190 = new A(a188.get());

        A a191 = new A(a189.get());
        A a192 = new A(a190.get());

        A a193 = new A(a191.get());
        A a194 = new A(a192.get());

        A a195 = new A(a193.get());
        A a196 = new A(a194.get());

        A a197 = new A(a195.get());
        A a198 = new A(a196.get());

        A a199 = new A(a197.get());
        A a200 = new A(a198.get());

        A a201 = new A(a199.get());
        A a202 = new A(a200.get());

        A a203 = new A(a201.get());
        A a204 = new A(a202.get());

        A a205 = new A(a203.get());
        A a206 = new A(a204.get());

        A a207 = new A(a205.get());
        A a208 = new A(a206.get());

        A a209 = new A(a207.get());
        A a210 = new A(a208.get());

        A a211 = new A(a209.get());
        A a212 = new A(a210.get());

        A a213 = new A(a211.get());
        A a214 = new A(a212.get());

        A a215 = new A(a213.get());
        A a216 = new A(a214.get());

        A a217 = new A(a215.get());
        A a218 = new A(a216.get());

        A a219 = new A(a217.get());
        A a220 = new A(a218.get());

        A a221 = new A(a219.get());
        A a222 = new A(a220.get());

        A a223 = new A(a221.get());
        A a224 = new A(a222.get());

        A a225 = new A(a223.get());
        A a226 = new A(a224.get());

        A a227 = new A(a225.get());
        A a228 = new A(a226.get());

        A a229 = new A(a227.get());
        A a230 = new A(a228.get());

        A a231 = new A(a229.get());
        A a232 = new A(a230.get());

        A a233 = new A(a231.get());
        A a234 = new A(a232.get());

        A a235 = new A(a233.get());
        A a236 = new A(a234.get());

        A a237 = new A(a235.get());
        A a238 = new A(a236.get());

        A a239 = new A(a237.get());
        A a240 = new A(a238.get());

        A a241 = new A(a239.get());
        A a242 = new A(a240.get());

        A a243 = new A(a241.get());
        A a244 = new A(a242.get());

        A a245 = new A(a243.get());
        A a246 = new A(a244.get());

        A a247 = new A(a245.get());
        A a248 = new A(a246.get());

        A a249 = new A(a247.get());
        A a250 = new A(a248.get());

        A a251 = new A(a249.get());
        A a252 = new A(a250.get());

        A a253 = new A(a251.get());
        A a254 = new A(a252.get());

        A a255 = new A(a253.get());
        A a256 = new A(a254.get());

        A a257 = new A(a255.get());
        A a258 = new A(a256.get());

        A a259 = new A(a257.get());
        A a260 = new A(a258.get());

        A a261 = new A(a259.get());
        A a262 = new A(a260.get());

        A a263 = new A(a261.get());
        A a264 = new A(a262.get());

        A a265 = new A(a263.get());
        A a266 = new A(a264.get());

        A a267 = new A(a265.get());
        A a268 = new A(a266.get());

        A a269 = new A(a267.get());
        A a270 = new A(a268.get());

        A a271 = new A(a269.get());
        A a272 = new A(a270.get());

        A a273 = new A(a271.get());
        A a274 = new A(a272.get());

        A a275 = new A(a273.get());
        A a276 = new A(a274.get());

        A a277 = new A(a275.get());
        A a278 = new A(a276.get());

        A a279 = new A(a277.get());
        A a280 = new A(a278.get());

        A a281 = new A(a279.get());
        A a282 = new A(a280.get());

        A a283 = new A(a281.get());
        A a284 = new A(a282.get());

        A a285 = new A(a283.get());
        A a286 = new A(a284.get());

        A a287 = new A(a285.get());
        A a288 = new A(a286.get());

        A a289 = new A(a287.get());
        A a290 = new A(a288.get());

        A a291 = new A(a289.get());
        A a292 = new A(a290.get());

        A a293 = new A(a291.get());
        A a294 = new A(a292.get());

        A a295 = new A(a293.get());
        A a296 = new A(a294.get());

        A a297 = new A(a295.get());
        A a298 = new A(a296.get());

        A a299 = new A(a297.get());
        A a300 = new A(a298.get());

        A a301 = new A(a299.get());
        A a302 = new A(a300.get());

        A a303 = new A(a301.get());
        A a304 = new A(a302.get());

        A a305 = new A(a303.get());
        A a306 = new A(a304.get());

        A a307 = new A(a305.get());
        A a308 = new A(a306.get());

        A a309 = new A(a307.get());
        A a310 = new A(a308.get());

        A a311 = new A(a309.get());
        A a312 = new A(a310.get());

        A a313 = new A(a311.get());
        A a314 = new A(a312.get());

        A a315 = new A(a313.get());
        A a316 = new A(a314.get());

        A a317 = new A(a315.get());
        A a318 = new A(a316.get());

        A a319 = new A(a317.get());
        A a320 = new A(a318.get());

        A a321 = new A(a319.get());
        A a322 = new A(a320.get());

        A a323 = new A(a321.get());
        A a324 = new A(a322.get());

        A a325 = new A(a323.get());
        A a326 = new A(a324.get());

        A a327 = new A(a325.get());
        A a328 = new A(a326.get());

        A a329 = new A(a327.get());
        A a330 = new A(a328.get());

        A a331 = new A(a329.get());
        A a332 = new A(a330.get());

        A a333 = new A(a331.get());
        A a334 = new A(a332.get());

        A a335 = new A(a333.get());
        A a336 = new A(a334.get());

        A a337 = new A(a335.get());
        A a338 = new A(a336.get());

        A a339 = new A(a337.get());
        A a340 = new A(a338.get());

        A a341 = new A(a339.get());
        A a342 = new A(a340.get());

        A a343 = new A(a341.get());
        A a344 = new A(a342.get());

        A a345 = new A(a343.get());
        A a346 = new A(a344.get());

        A a347 = new A(a345.get());
        A a348 = new A(a346.get());

        A a349 = new A(a347.get());
        A a350 = new A(a348.get());

        A a351 = new A(a349.get());
        A a352 = new A(a350.get());

        A a353 = new A(a351.get());
        A a354 = new A(a352.get());

        A a355 = new A(a353.get());
        A a356 = new A(a354.get());

        A a357 = new A(a355.get());
        A a358 = new A(a356.get());

        A a359 = new A(a357.get());
        A a360 = new A(a358.get());

        A a361 = new A(a359.get());
        A a362 = new A(a360.get());

        A a363 = new A(a361.get());
        A a364 = new A(a362.get());

        A a365 = new A(a363.get());
        A a366 = new A(a364.get());

        A a367 = new A(a365.get());
        A a368 = new A(a366.get());

        A a369 = new A(a367.get());
        A a370 = new A(a368.get());

        A a371 = new A(a369.get());
        A a372 = new A(a370.get());

        A a373 = new A(a371.get());
        A a374 = new A(a372.get());

        A a375 = new A(a373.get());
        A a376 = new A(a374.get());

        A a377 = new A(a375.get());
        A a378 = new A(a376.get());

        A a379 = new A(a377.get());
        A a380 = new A(a378.get());

        A a381 = new A(a379.get());
        A a382 = new A(a380.get());

        A a383 = new A(a381.get());
        A a384 = new A(a382.get());

        A a385 = new A(a383.get());
        A a386 = new A(a384.get());

        A a387 = new A(a385.get());
        A a388 = new A(a386.get());

        A a389 = new A(a387.get());
        A a390 = new A(a388.get());

        A a391 = new A(a389.get());
        A a392 = new A(a390.get());

        A a393 = new A(a391.get());
        A a394 = new A(a392.get());

        A a395 = new A(a393.get());
        A a396 = new A(a394.get());

        A a397 = new A(a395.get());
        A a398 = new A(a396.get());

        A a399 = new A(a397.get());
        A a400 = new A(a398.get());

        A a401 = new A(a399.get());
        A a402 = new A(a400.get());

        A a403 = new A(a401.get());
        A a404 = new A(a402.get());

        A a405 = new A(a403.get());
        A a406 = new A(a404.get());

        A a407 = new A(a405.get());
        A a408 = new A(a406.get());

        A a409 = new A(a407.get());
        A a410 = new A(a408.get());

        A a411 = new A(a409.get());
        A a412 = new A(a410.get());

        A a413 = new A(a411.get());
        A a414 = new A(a412.get());

        A a415 = new A(a413.get());
        A a416 = new A(a414.get());

        A a417 = new A(a415.get());
        A a418 = new A(a416.get());

        A a419 = new A(a417.get());
        A a420 = new A(a418.get());

        A a421 = new A(a419.get());
        A a422 = new A(a420.get());

        A a423 = new A(a421.get());
        A a424 = new A(a422.get());

        A a425 = new A(a423.get());
        A a426 = new A(a424.get());

        A a427 = new A(a425.get());
        A a428 = new A(a426.get());

        A a429 = new A(a427.get());
        A a430 = new A(a428.get());

        A a431 = new A(a429.get());
        A a432 = new A(a430.get());

        A a433 = new A(a431.get());
        A a434 = new A(a432.get());

        A a435 = new A(a433.get());
        A a436 = new A(a434.get());

        A a437 = new A(a435.get());
        A a438 = new A(a436.get());

        A a439 = new A(a437.get());
        A a440 = new A(a438.get());

        A a441 = new A(a439.get());
        A a442 = new A(a440.get());

        A a443 = new A(a441.get());
        A a444 = new A(a442.get());

        A a445 = new A(a443.get());
        A a446 = new A(a444.get());

        A a447 = new A(a445.get());
        A a448 = new A(a446.get());

        A a449 = new A(a447.get());
        A a450 = new A(a448.get());

        A a451 = new A(a449.get());
        A a452 = new A(a450.get());

        A a453 = new A(a451.get());
        A a454 = new A(a452.get());

        A a455 = new A(a453.get());
        A a456 = new A(a454.get());

        A a457 = new A(a455.get());
        A a458 = new A(a456.get());

        A a459 = new A(a457.get());
        A a460 = new A(a458.get());

        A a461 = new A(a459.get());
        A a462 = new A(a460.get());

        A a463 = new A(a461.get());
        A a464 = new A(a462.get());

        A a465 = new A(a463.get());
        A a466 = new A(a464.get());

        A a467 = new A(a465.get());
        A a468 = new A(a466.get());

        A a469 = new A(a467.get());
        A a470 = new A(a468.get());

        A a471 = new A(a469.get());
        A a472 = new A(a470.get());

        A a473 = new A(a471.get());
        A a474 = new A(a472.get());

        A a475 = new A(a473.get());
        A a476 = new A(a474.get());

        A a477 = new A(a475.get());
        A a478 = new A(a476.get());

        A a479 = new A(a477.get());
        A a480 = new A(a478.get());

        A a481 = new A(a479.get());
        A a482 = new A(a480.get());

        A a483 = new A(a481.get());
        A a484 = new A(a482.get());

        A a485 = new A(a483.get());
        A a486 = new A(a484.get());

        A a487 = new A(a485.get());
        A a488 = new A(a486.get());

        A a489 = new A(a487.get());
        A a490 = new A(a488.get());

        A a491 = new A(a489.get());
        A a492 = new A(a490.get());

        A a493 = new A(a491.get());
        A a494 = new A(a492.get());

        A a495 = new A(a493.get());
        A a496 = new A(a494.get());

        A a497 = new A(a495.get());
        A a498 = new A(a496.get());

        A a499 = new A(a497.get());
        A a500 = new A(a498.get());
        return foo1(a499, a500);
      }
      
      public static boolean foo1(A a499, A a500) {
        A a501 = new A(a499.get());
        A a502 = new A(a500.get());

        A a503 = new A(a501.get());
        A a504 = new A(a502.get());

        A a505 = new A(a503.get());
        A a506 = new A(a504.get());

        A a507 = new A(a505.get());
        A a508 = new A(a506.get());

        A a509 = new A(a507.get());
        A a510 = new A(a508.get());

        A a511 = new A(a509.get());
        A a512 = new A(a510.get());

        A a513 = new A(a511.get());
        A a514 = new A(a512.get());

        A a515 = new A(a513.get());
        A a516 = new A(a514.get());

        A a517 = new A(a515.get());
        A a518 = new A(a516.get());

        A a519 = new A(a517.get());
        A a520 = new A(a518.get());

        A a521 = new A(a519.get());
        A a522 = new A(a520.get());

        A a523 = new A(a521.get());
        A a524 = new A(a522.get());

        A a525 = new A(a523.get());
        A a526 = new A(a524.get());

        A a527 = new A(a525.get());
        A a528 = new A(a526.get());

        A a529 = new A(a527.get());
        A a530 = new A(a528.get());

        A a531 = new A(a529.get());
        A a532 = new A(a530.get());

        A a533 = new A(a531.get());
        A a534 = new A(a532.get());

        A a535 = new A(a533.get());
        A a536 = new A(a534.get());

        A a537 = new A(a535.get());
        A a538 = new A(a536.get());

        A a539 = new A(a537.get());
        A a540 = new A(a538.get());

        A a541 = new A(a539.get());
        A a542 = new A(a540.get());

        A a543 = new A(a541.get());
        A a544 = new A(a542.get());

        A a545 = new A(a543.get());
        A a546 = new A(a544.get());

        A a547 = new A(a545.get());
        A a548 = new A(a546.get());

        A a549 = new A(a547.get());
        A a550 = new A(a548.get());

        A a551 = new A(a549.get());
        A a552 = new A(a550.get());

        A a553 = new A(a551.get());
        A a554 = new A(a552.get());

        A a555 = new A(a553.get());
        A a556 = new A(a554.get());

        A a557 = new A(a555.get());
        A a558 = new A(a556.get());

        A a559 = new A(a557.get());
        A a560 = new A(a558.get());

        A a561 = new A(a559.get());
        A a562 = new A(a560.get());

        A a563 = new A(a561.get());
        A a564 = new A(a562.get());

        A a565 = new A(a563.get());
        A a566 = new A(a564.get());

        A a567 = new A(a565.get());
        A a568 = new A(a566.get());

        A a569 = new A(a567.get());
        A a570 = new A(a568.get());

        A a571 = new A(a569.get());
        A a572 = new A(a570.get());

        A a573 = new A(a571.get());
        A a574 = new A(a572.get());

        A a575 = new A(a573.get());
        A a576 = new A(a574.get());

        A a577 = new A(a575.get());
        A a578 = new A(a576.get());

        A a579 = new A(a577.get());
        A a580 = new A(a578.get());

        A a581 = new A(a579.get());
        A a582 = new A(a580.get());

        A a583 = new A(a581.get());
        A a584 = new A(a582.get());

        A a585 = new A(a583.get());
        A a586 = new A(a584.get());

        A a587 = new A(a585.get());
        A a588 = new A(a586.get());

        A a589 = new A(a587.get());
        A a590 = new A(a588.get());

        A a591 = new A(a589.get());
        A a592 = new A(a590.get());

        A a593 = new A(a591.get());
        A a594 = new A(a592.get());

        A a595 = new A(a593.get());
        A a596 = new A(a594.get());

        A a597 = new A(a595.get());
        A a598 = new A(a596.get());

        A a599 = new A(a597.get());
        A a600 = new A(a598.get());

        A a601 = new A(a599.get());
        A a602 = new A(a600.get());

        A a603 = new A(a601.get());
        A a604 = new A(a602.get());

        A a605 = new A(a603.get());
        A a606 = new A(a604.get());

        A a607 = new A(a605.get());
        A a608 = new A(a606.get());

        A a609 = new A(a607.get());
        A a610 = new A(a608.get());

        A a611 = new A(a609.get());
        A a612 = new A(a610.get());

        A a613 = new A(a611.get());
        A a614 = new A(a612.get());

        A a615 = new A(a613.get());
        A a616 = new A(a614.get());

        A a617 = new A(a615.get());
        A a618 = new A(a616.get());

        A a619 = new A(a617.get());
        A a620 = new A(a618.get());

        A a621 = new A(a619.get());
        A a622 = new A(a620.get());

        A a623 = new A(a621.get());
        A a624 = new A(a622.get());

        A a625 = new A(a623.get());
        A a626 = new A(a624.get());

        A a627 = new A(a625.get());
        A a628 = new A(a626.get());

        A a629 = new A(a627.get());
        A a630 = new A(a628.get());

        A a631 = new A(a629.get());
        A a632 = new A(a630.get());

        A a633 = new A(a631.get());
        A a634 = new A(a632.get());

        A a635 = new A(a633.get());
        A a636 = new A(a634.get());

        A a637 = new A(a635.get());
        A a638 = new A(a636.get());

        A a639 = new A(a637.get());
        A a640 = new A(a638.get());

        A a641 = new A(a639.get());
        A a642 = new A(a640.get());

        A a643 = new A(a641.get());
        A a644 = new A(a642.get());

        A a645 = new A(a643.get());
        A a646 = new A(a644.get());

        A a647 = new A(a645.get());
        A a648 = new A(a646.get());

        A a649 = new A(a647.get());
        A a650 = new A(a648.get());

        A a651 = new A(a649.get());
        A a652 = new A(a650.get());

        A a653 = new A(a651.get());
        A a654 = new A(a652.get());

        A a655 = new A(a653.get());
        A a656 = new A(a654.get());

        A a657 = new A(a655.get());
        A a658 = new A(a656.get());

        A a659 = new A(a657.get());
        A a660 = new A(a658.get());

        A a661 = new A(a659.get());
        A a662 = new A(a660.get());

        A a663 = new A(a661.get());
        A a664 = new A(a662.get());

        A a665 = new A(a663.get());
        A a666 = new A(a664.get());

        A a667 = new A(a665.get());
        A a668 = new A(a666.get());

        A a669 = new A(a667.get());
        A a670 = new A(a668.get());

        A a671 = new A(a669.get());
        A a672 = new A(a670.get());

        A a673 = new A(a671.get());
        A a674 = new A(a672.get());

        A a675 = new A(a673.get());
        A a676 = new A(a674.get());

        A a677 = new A(a675.get());
        A a678 = new A(a676.get());

        A a679 = new A(a677.get());
        A a680 = new A(a678.get());

        A a681 = new A(a679.get());
        A a682 = new A(a680.get());

        A a683 = new A(a681.get());
        A a684 = new A(a682.get());

        A a685 = new A(a683.get());
        A a686 = new A(a684.get());

        A a687 = new A(a685.get());
        A a688 = new A(a686.get());

        A a689 = new A(a687.get());
        A a690 = new A(a688.get());

        A a691 = new A(a689.get());
        A a692 = new A(a690.get());

        A a693 = new A(a691.get());
        A a694 = new A(a692.get());

        A a695 = new A(a693.get());
        A a696 = new A(a694.get());

        A a697 = new A(a695.get());
        A a698 = new A(a696.get());

        A a699 = new A(a697.get());
        A a700 = new A(a698.get());

        A a701 = new A(a699.get());
        A a702 = new A(a700.get());

        A a703 = new A(a701.get());
        A a704 = new A(a702.get());

        A a705 = new A(a703.get());
        A a706 = new A(a704.get());

        A a707 = new A(a705.get());
        A a708 = new A(a706.get());

        A a709 = new A(a707.get());
        A a710 = new A(a708.get());

        A a711 = new A(a709.get());
        A a712 = new A(a710.get());

        A a713 = new A(a711.get());
        A a714 = new A(a712.get());

        A a715 = new A(a713.get());
        A a716 = new A(a714.get());

        A a717 = new A(a715.get());
        A a718 = new A(a716.get());

        A a719 = new A(a717.get());
        A a720 = new A(a718.get());

        A a721 = new A(a719.get());
        A a722 = new A(a720.get());

        A a723 = new A(a721.get());
        A a724 = new A(a722.get());

        A a725 = new A(a723.get());
        A a726 = new A(a724.get());

        A a727 = new A(a725.get());
        A a728 = new A(a726.get());

        A a729 = new A(a727.get());
        A a730 = new A(a728.get());

        A a731 = new A(a729.get());
        A a732 = new A(a730.get());

        A a733 = new A(a731.get());
        A a734 = new A(a732.get());

        A a735 = new A(a733.get());
        A a736 = new A(a734.get());

        A a737 = new A(a735.get());
        A a738 = new A(a736.get());

        A a739 = new A(a737.get());
        A a740 = new A(a738.get());

        A a741 = new A(a739.get());
        A a742 = new A(a740.get());

        A a743 = new A(a741.get());
        A a744 = new A(a742.get());

        A a745 = new A(a743.get());
        A a746 = new A(a744.get());

        A a747 = new A(a745.get());
        A a748 = new A(a746.get());

        A a749 = new A(a747.get());
        A a750 = new A(a748.get());

        A a751 = new A(a749.get());
        A a752 = new A(a750.get());

        A a753 = new A(a751.get());
        A a754 = new A(a752.get());

        A a755 = new A(a753.get());
        A a756 = new A(a754.get());

        A a757 = new A(a755.get());
        A a758 = new A(a756.get());

        A a759 = new A(a757.get());
        A a760 = new A(a758.get());

        A a761 = new A(a759.get());
        A a762 = new A(a760.get());

        A a763 = new A(a761.get());
        A a764 = new A(a762.get());

        A a765 = new A(a763.get());
        A a766 = new A(a764.get());

        A a767 = new A(a765.get());
        A a768 = new A(a766.get());

        A a769 = new A(a767.get());
        A a770 = new A(a768.get());

        A a771 = new A(a769.get());
        A a772 = new A(a770.get());

        A a773 = new A(a771.get());
        A a774 = new A(a772.get());

        A a775 = new A(a773.get());
        A a776 = new A(a774.get());

        A a777 = new A(a775.get());
        A a778 = new A(a776.get());

        A a779 = new A(a777.get());
        A a780 = new A(a778.get());

        A a781 = new A(a779.get());
        A a782 = new A(a780.get());

        A a783 = new A(a781.get());
        A a784 = new A(a782.get());

        A a785 = new A(a783.get());
        A a786 = new A(a784.get());

        A a787 = new A(a785.get());
        A a788 = new A(a786.get());

        A a789 = new A(a787.get());
        A a790 = new A(a788.get());

        A a791 = new A(a789.get());
        A a792 = new A(a790.get());

        A a793 = new A(a791.get());
        A a794 = new A(a792.get());

        A a795 = new A(a793.get());
        A a796 = new A(a794.get());

        A a797 = new A(a795.get());
        A a798 = new A(a796.get());

        A a799 = new A(a797.get());
        A a800 = new A(a798.get());

        A a801 = new A(a799.get());
        A a802 = new A(a800.get());

        A a803 = new A(a801.get());
        A a804 = new A(a802.get());

        A a805 = new A(a803.get());
        A a806 = new A(a804.get());

        A a807 = new A(a805.get());
        A a808 = new A(a806.get());

        A a809 = new A(a807.get());
        A a810 = new A(a808.get());

        A a811 = new A(a809.get());
        A a812 = new A(a810.get());

        A a813 = new A(a811.get());
        A a814 = new A(a812.get());

        A a815 = new A(a813.get());
        A a816 = new A(a814.get());

        A a817 = new A(a815.get());
        A a818 = new A(a816.get());

        A a819 = new A(a817.get());
        A a820 = new A(a818.get());

        A a821 = new A(a819.get());
        A a822 = new A(a820.get());

        A a823 = new A(a821.get());
        A a824 = new A(a822.get());

        A a825 = new A(a823.get());
        A a826 = new A(a824.get());

        A a827 = new A(a825.get());
        A a828 = new A(a826.get());

        A a829 = new A(a827.get());
        A a830 = new A(a828.get());

        A a831 = new A(a829.get());
        A a832 = new A(a830.get());

        A a833 = new A(a831.get());
        A a834 = new A(a832.get());

        A a835 = new A(a833.get());
        A a836 = new A(a834.get());

        A a837 = new A(a835.get());
        A a838 = new A(a836.get());

        A a839 = new A(a837.get());
        A a840 = new A(a838.get());

        A a841 = new A(a839.get());
        A a842 = new A(a840.get());

        A a843 = new A(a841.get());
        A a844 = new A(a842.get());

        A a845 = new A(a843.get());
        A a846 = new A(a844.get());

        A a847 = new A(a845.get());
        A a848 = new A(a846.get());

        A a849 = new A(a847.get());
        A a850 = new A(a848.get());

        A a851 = new A(a849.get());
        A a852 = new A(a850.get());

        A a853 = new A(a851.get());
        A a854 = new A(a852.get());

        A a855 = new A(a853.get());
        A a856 = new A(a854.get());

        A a857 = new A(a855.get());
        A a858 = new A(a856.get());

        A a859 = new A(a857.get());
        A a860 = new A(a858.get());

        A a861 = new A(a859.get());
        A a862 = new A(a860.get());

        A a863 = new A(a861.get());
        A a864 = new A(a862.get());

        A a865 = new A(a863.get());
        A a866 = new A(a864.get());

        A a867 = new A(a865.get());
        A a868 = new A(a866.get());

        A a869 = new A(a867.get());
        A a870 = new A(a868.get());

        A a871 = new A(a869.get());
        A a872 = new A(a870.get());

        A a873 = new A(a871.get());
        A a874 = new A(a872.get());

        A a875 = new A(a873.get());
        A a876 = new A(a874.get());

        A a877 = new A(a875.get());
        A a878 = new A(a876.get());

        A a879 = new A(a877.get());
        A a880 = new A(a878.get());

        A a881 = new A(a879.get());
        A a882 = new A(a880.get());

        A a883 = new A(a881.get());
        A a884 = new A(a882.get());

        A a885 = new A(a883.get());
        A a886 = new A(a884.get());

        A a887 = new A(a885.get());
        A a888 = new A(a886.get());

        A a889 = new A(a887.get());
        A a890 = new A(a888.get());

        A a891 = new A(a889.get());
        A a892 = new A(a890.get());

        A a893 = new A(a891.get());
        A a894 = new A(a892.get());

        A a895 = new A(a893.get());
        A a896 = new A(a894.get());

        A a897 = new A(a895.get());
        A a898 = new A(a896.get());

        A a899 = new A(a897.get());
        A a900 = new A(a898.get());

        A a901 = new A(a899.get());
        A a902 = new A(a900.get());

        A a903 = new A(a901.get());
        A a904 = new A(a902.get());

        A a905 = new A(a903.get());
        A a906 = new A(a904.get());

        A a907 = new A(a905.get());
        A a908 = new A(a906.get());

        A a909 = new A(a907.get());
        A a910 = new A(a908.get());

        A a911 = new A(a909.get());
        A a912 = new A(a910.get());

        A a913 = new A(a911.get());
        A a914 = new A(a912.get());

        A a915 = new A(a913.get());
        A a916 = new A(a914.get());

        A a917 = new A(a915.get());
        A a918 = new A(a916.get());

        A a919 = new A(a917.get());
        A a920 = new A(a918.get());

        A a921 = new A(a919.get());
        A a922 = new A(a920.get());

        A a923 = new A(a921.get());
        A a924 = new A(a922.get());

        A a925 = new A(a923.get());
        A a926 = new A(a924.get());

        A a927 = new A(a925.get());
        A a928 = new A(a926.get());

        A a929 = new A(a927.get());
        A a930 = new A(a928.get());

        A a931 = new A(a929.get());
        A a932 = new A(a930.get());

        A a933 = new A(a931.get());
        A a934 = new A(a932.get());

        A a935 = new A(a933.get());
        A a936 = new A(a934.get());

        A a937 = new A(a935.get());
        A a938 = new A(a936.get());

        A a939 = new A(a937.get());
        A a940 = new A(a938.get());

        A a941 = new A(a939.get());
        A a942 = new A(a940.get());

        A a943 = new A(a941.get());
        A a944 = new A(a942.get());

        A a945 = new A(a943.get());
        A a946 = new A(a944.get());

        A a947 = new A(a945.get());
        A a948 = new A(a946.get());

        A a949 = new A(a947.get());
        A a950 = new A(a948.get());

        A a951 = new A(a949.get());
        A a952 = new A(a950.get());

        A a953 = new A(a951.get());
        A a954 = new A(a952.get());

        A a955 = new A(a953.get());
        A a956 = new A(a954.get());

        A a957 = new A(a955.get());
        A a958 = new A(a956.get());

        A a959 = new A(a957.get());
        A a960 = new A(a958.get());

        A a961 = new A(a959.get());
        A a962 = new A(a960.get());

        A a963 = new A(a961.get());
        A a964 = new A(a962.get());

        A a965 = new A(a963.get());
        A a966 = new A(a964.get());

        A a967 = new A(a965.get());
        A a968 = new A(a966.get());

        A a969 = new A(a967.get());
        A a970 = new A(a968.get());

        A a971 = new A(a969.get());
        A a972 = new A(a970.get());

        A a973 = new A(a971.get());
        A a974 = new A(a972.get());

        A a975 = new A(a973.get());
        A a976 = new A(a974.get());

        A a977 = new A(a975.get());
        A a978 = new A(a976.get());

        A a979 = new A(a977.get());
        A a980 = new A(a978.get());

        A a981 = new A(a979.get());
        A a982 = new A(a980.get());

        A a983 = new A(a981.get());
        A a984 = new A(a982.get());

        A a985 = new A(a983.get());
        A a986 = new A(a984.get());

        A a987 = new A(a985.get());
        A a988 = new A(a986.get());

        A a989 = new A(a987.get());
        A a990 = new A(a988.get());

        A a991 = new A(a989.get());
        A a992 = new A(a990.get());

        A a993 = new A(a991.get());
        A a994 = new A(a992.get());

        A a995 = new A(a993.get());
        A a996 = new A(a994.get());

        A a997 = new A(a995.get());
        A a998 = new A(a996.get());

        A a999 = new A(a997.get());
        A a1000 = new A(a998.get());

        return foo2(a999, a1000);
      }
        
      public static boolean foo2(A a999, A a1000) {
        	
        A a1001 = new A(a999.get());
        A a1002 = new A(a1000.get());

        A a1003 = new A(a1001.get());
        A a1004 = new A(a1002.get());

        A a1005 = new A(a1003.get());
        A a1006 = new A(a1004.get());

        A a1007 = new A(a1005.get());
        A a1008 = new A(a1006.get());

        A a1009 = new A(a1007.get());
        A a1010 = new A(a1008.get());

        A a1011 = new A(a1009.get());
        A a1012 = new A(a1010.get());

        A a1013 = new A(a1011.get());
        A a1014 = new A(a1012.get());

        A a1015 = new A(a1013.get());
        A a1016 = new A(a1014.get());

        A a1017 = new A(a1015.get());
        A a1018 = new A(a1016.get());

        A a1019 = new A(a1017.get());
        A a1020 = new A(a1018.get());

        A a1021 = new A(a1019.get());
        A a1022 = new A(a1020.get());

        A a1023 = new A(a1021.get());
        A a1024 = new A(a1022.get());

        A a1025 = new A(a1023.get());
        A a1026 = new A(a1024.get());

        A a1027 = new A(a1025.get());
        A a1028 = new A(a1026.get());

        A a1029 = new A(a1027.get());
        A a1030 = new A(a1028.get());

        A a1031 = new A(a1029.get());
        A a1032 = new A(a1030.get());

        A a1033 = new A(a1031.get());
        A a1034 = new A(a1032.get());

        A a1035 = new A(a1033.get());
        A a1036 = new A(a1034.get());

        A a1037 = new A(a1035.get());
        A a1038 = new A(a1036.get());

        A a1039 = new A(a1037.get());
        A a1040 = new A(a1038.get());

        A a1041 = new A(a1039.get());
        A a1042 = new A(a1040.get());

        A a1043 = new A(a1041.get());
        A a1044 = new A(a1042.get());

        A a1045 = new A(a1043.get());
        A a1046 = new A(a1044.get());

        A a1047 = new A(a1045.get());
        A a1048 = new A(a1046.get());

        A a1049 = new A(a1047.get());
        A a1050 = new A(a1048.get());

        A a1051 = new A(a1049.get());
        A a1052 = new A(a1050.get());

        A a1053 = new A(a1051.get());
        A a1054 = new A(a1052.get());

        A a1055 = new A(a1053.get());
        A a1056 = new A(a1054.get());

        A a1057 = new A(a1055.get());
        A a1058 = new A(a1056.get());

        A a1059 = new A(a1057.get());
        A a1060 = new A(a1058.get());

        A a1061 = new A(a1059.get());
        A a1062 = new A(a1060.get());

        A a1063 = new A(a1061.get());
        A a1064 = new A(a1062.get());

        A a1065 = new A(a1063.get());
        A a1066 = new A(a1064.get());

        A a1067 = new A(a1065.get());
        A a1068 = new A(a1066.get());

        A a1069 = new A(a1067.get());
        A a1070 = new A(a1068.get());

        A a1071 = new A(a1069.get());
        A a1072 = new A(a1070.get());

        A a1073 = new A(a1071.get());
        A a1074 = new A(a1072.get());

        A a1075 = new A(a1073.get());
        A a1076 = new A(a1074.get());

        A a1077 = new A(a1075.get());
        A a1078 = new A(a1076.get());

        A a1079 = new A(a1077.get());
        A a1080 = new A(a1078.get());

        A a1081 = new A(a1079.get());
        A a1082 = new A(a1080.get());

        A a1083 = new A(a1081.get());
        A a1084 = new A(a1082.get());

        A a1085 = new A(a1083.get());
        A a1086 = new A(a1084.get());

        A a1087 = new A(a1085.get());
        A a1088 = new A(a1086.get());

        A a1089 = new A(a1087.get());
        A a1090 = new A(a1088.get());

        A a1091 = new A(a1089.get());
        A a1092 = new A(a1090.get());

        A a1093 = new A(a1091.get());
        A a1094 = new A(a1092.get());

        A a1095 = new A(a1093.get());
        A a1096 = new A(a1094.get());

        A a1097 = new A(a1095.get());
        A a1098 = new A(a1096.get());

        A a1099 = new A(a1097.get());
        A a1100 = new A(a1098.get());

        A a1101 = new A(a1099.get());
        A a1102 = new A(a1100.get());

        A a1103 = new A(a1101.get());
        A a1104 = new A(a1102.get());

        A a1105 = new A(a1103.get());
        A a1106 = new A(a1104.get());

        A a1107 = new A(a1105.get());
        A a1108 = new A(a1106.get());

        A a1109 = new A(a1107.get());
        A a1110 = new A(a1108.get());

        A a1111 = new A(a1109.get());
        A a1112 = new A(a1110.get());

        A a1113 = new A(a1111.get());
        A a1114 = new A(a1112.get());

        A a1115 = new A(a1113.get());
        A a1116 = new A(a1114.get());

        A a1117 = new A(a1115.get());
        A a1118 = new A(a1116.get());

        A a1119 = new A(a1117.get());
        A a1120 = new A(a1118.get());

        A a1121 = new A(a1119.get());
        A a1122 = new A(a1120.get());

        A a1123 = new A(a1121.get());
        A a1124 = new A(a1122.get());

        A a1125 = new A(a1123.get());
        A a1126 = new A(a1124.get());

        A a1127 = new A(a1125.get());
        A a1128 = new A(a1126.get());

        A a1129 = new A(a1127.get());
        A a1130 = new A(a1128.get());

        A a1131 = new A(a1129.get());
        A a1132 = new A(a1130.get());

        A a1133 = new A(a1131.get());
        A a1134 = new A(a1132.get());

        A a1135 = new A(a1133.get());
        A a1136 = new A(a1134.get());

        A a1137 = new A(a1135.get());
        A a1138 = new A(a1136.get());

        A a1139 = new A(a1137.get());
        A a1140 = new A(a1138.get());

        A a1141 = new A(a1139.get());
        A a1142 = new A(a1140.get());

        A a1143 = new A(a1141.get());
        A a1144 = new A(a1142.get());

        A a1145 = new A(a1143.get());
        A a1146 = new A(a1144.get());

        A a1147 = new A(a1145.get());
        A a1148 = new A(a1146.get());

        A a1149 = new A(a1147.get());
        A a1150 = new A(a1148.get());

        A a1151 = new A(a1149.get());
        A a1152 = new A(a1150.get());

        A a1153 = new A(a1151.get());
        A a1154 = new A(a1152.get());

        A a1155 = new A(a1153.get());
        A a1156 = new A(a1154.get());

        A a1157 = new A(a1155.get());
        A a1158 = new A(a1156.get());

        A a1159 = new A(a1157.get());
        A a1160 = new A(a1158.get());

        A a1161 = new A(a1159.get());
        A a1162 = new A(a1160.get());

        A a1163 = new A(a1161.get());
        A a1164 = new A(a1162.get());

        A a1165 = new A(a1163.get());
        A a1166 = new A(a1164.get());

        A a1167 = new A(a1165.get());
        A a1168 = new A(a1166.get());

        A a1169 = new A(a1167.get());
        A a1170 = new A(a1168.get());

        A a1171 = new A(a1169.get());
        A a1172 = new A(a1170.get());

        A a1173 = new A(a1171.get());
        A a1174 = new A(a1172.get());

        A a1175 = new A(a1173.get());
        A a1176 = new A(a1174.get());

        A a1177 = new A(a1175.get());
        A a1178 = new A(a1176.get());

        A a1179 = new A(a1177.get());
        A a1180 = new A(a1178.get());

        A a1181 = new A(a1179.get());
        A a1182 = new A(a1180.get());

        A a1183 = new A(a1181.get());
        A a1184 = new A(a1182.get());

        A a1185 = new A(a1183.get());
        A a1186 = new A(a1184.get());

        A a1187 = new A(a1185.get());
        A a1188 = new A(a1186.get());

        A a1189 = new A(a1187.get());
        A a1190 = new A(a1188.get());

        A a1191 = new A(a1189.get());
        A a1192 = new A(a1190.get());

        A a1193 = new A(a1191.get());
        A a1194 = new A(a1192.get());

        A a1195 = new A(a1193.get());
        A a1196 = new A(a1194.get());

        A a1197 = new A(a1195.get());
        A a1198 = new A(a1196.get());

        A a1199 = new A(a1197.get());
        A a1200 = new A(a1198.get());

        A a1201 = new A(a1199.get());
        A a1202 = new A(a1200.get());

        A a1203 = new A(a1201.get());
        A a1204 = new A(a1202.get());

        A a1205 = new A(a1203.get());
        A a1206 = new A(a1204.get());

        A a1207 = new A(a1205.get());
        A a1208 = new A(a1206.get());

        A a1209 = new A(a1207.get());
        A a1210 = new A(a1208.get());

        A a1211 = new A(a1209.get());
        A a1212 = new A(a1210.get());

        A a1213 = new A(a1211.get());
        A a1214 = new A(a1212.get());

        A a1215 = new A(a1213.get());
        A a1216 = new A(a1214.get());

        A a1217 = new A(a1215.get());
        A a1218 = new A(a1216.get());

        A a1219 = new A(a1217.get());
        A a1220 = new A(a1218.get());

        A a1221 = new A(a1219.get());
        A a1222 = new A(a1220.get());

        A a1223 = new A(a1221.get());
        A a1224 = new A(a1222.get());

        A a1225 = new A(a1223.get());
        A a1226 = new A(a1224.get());

        A a1227 = new A(a1225.get());
        A a1228 = new A(a1226.get());

        A a1229 = new A(a1227.get());
        A a1230 = new A(a1228.get());

        A a1231 = new A(a1229.get());
        A a1232 = new A(a1230.get());

        A a1233 = new A(a1231.get());
        A a1234 = new A(a1232.get());

        A a1235 = new A(a1233.get());
        A a1236 = new A(a1234.get());

        A a1237 = new A(a1235.get());
        A a1238 = new A(a1236.get());

        A a1239 = new A(a1237.get());
        A a1240 = new A(a1238.get());

        A a1241 = new A(a1239.get());
        A a1242 = new A(a1240.get());

        A a1243 = new A(a1241.get());
        A a1244 = new A(a1242.get());

        A a1245 = new A(a1243.get());
        A a1246 = new A(a1244.get());

        A a1247 = new A(a1245.get());
        A a1248 = new A(a1246.get());

        A a1249 = new A(a1247.get());
        A a1250 = new A(a1248.get());

        A a1251 = new A(a1249.get());
        A a1252 = new A(a1250.get());

        A a1253 = new A(a1251.get());
        A a1254 = new A(a1252.get());

        A a1255 = new A(a1253.get());
        A a1256 = new A(a1254.get());

        A a1257 = new A(a1255.get());
        A a1258 = new A(a1256.get());

        A a1259 = new A(a1257.get());
        A a1260 = new A(a1258.get());

        A a1261 = new A(a1259.get());
        A a1262 = new A(a1260.get());

        A a1263 = new A(a1261.get());
        A a1264 = new A(a1262.get());

        A a1265 = new A(a1263.get());
        A a1266 = new A(a1264.get());

        A a1267 = new A(a1265.get());
        A a1268 = new A(a1266.get());

        A a1269 = new A(a1267.get());
        A a1270 = new A(a1268.get());

        A a1271 = new A(a1269.get());
        A a1272 = new A(a1270.get());

        A a1273 = new A(a1271.get());
        A a1274 = new A(a1272.get());

        A a1275 = new A(a1273.get());
        A a1276 = new A(a1274.get());

        A a1277 = new A(a1275.get());
        A a1278 = new A(a1276.get());

        A a1279 = new A(a1277.get());
        A a1280 = new A(a1278.get());

        A a1281 = new A(a1279.get());
        A a1282 = new A(a1280.get());

        A a1283 = new A(a1281.get());
        A a1284 = new A(a1282.get());

        A a1285 = new A(a1283.get());
        A a1286 = new A(a1284.get());

        A a1287 = new A(a1285.get());
        A a1288 = new A(a1286.get());

        A a1289 = new A(a1287.get());
        A a1290 = new A(a1288.get());

        A a1291 = new A(a1289.get());
        A a1292 = new A(a1290.get());

        A a1293 = new A(a1291.get());
        A a1294 = new A(a1292.get());

        A a1295 = new A(a1293.get());
        A a1296 = new A(a1294.get());

        A a1297 = new A(a1295.get());
        A a1298 = new A(a1296.get());

        A a1299 = new A(a1297.get());
        A a1300 = new A(a1298.get());

        A a1301 = new A(a1299.get());
        A a1302 = new A(a1300.get());

        A a1303 = new A(a1301.get());
        A a1304 = new A(a1302.get());

        A a1305 = new A(a1303.get());
        A a1306 = new A(a1304.get());

        A a1307 = new A(a1305.get());
        A a1308 = new A(a1306.get());

        A a1309 = new A(a1307.get());
        A a1310 = new A(a1308.get());

        A a1311 = new A(a1309.get());
        A a1312 = new A(a1310.get());

        A a1313 = new A(a1311.get());
        A a1314 = new A(a1312.get());

        A a1315 = new A(a1313.get());
        A a1316 = new A(a1314.get());

        A a1317 = new A(a1315.get());
        A a1318 = new A(a1316.get());

        A a1319 = new A(a1317.get());
        A a1320 = new A(a1318.get());

        A a1321 = new A(a1319.get());
        A a1322 = new A(a1320.get());

        A a1323 = new A(a1321.get());
        A a1324 = new A(a1322.get());

        A a1325 = new A(a1323.get());
        A a1326 = new A(a1324.get());

        A a1327 = new A(a1325.get());
        A a1328 = new A(a1326.get());

        A a1329 = new A(a1327.get());
        A a1330 = new A(a1328.get());

        A a1331 = new A(a1329.get());
        A a1332 = new A(a1330.get());

        A a1333 = new A(a1331.get());
        A a1334 = new A(a1332.get());

        A a1335 = new A(a1333.get());
        A a1336 = new A(a1334.get());

        A a1337 = new A(a1335.get());
        A a1338 = new A(a1336.get());

        A a1339 = new A(a1337.get());
        A a1340 = new A(a1338.get());

        A a1341 = new A(a1339.get());
        A a1342 = new A(a1340.get());

        A a1343 = new A(a1341.get());
        A a1344 = new A(a1342.get());

        A a1345 = new A(a1343.get());
        A a1346 = new A(a1344.get());

        A a1347 = new A(a1345.get());
        A a1348 = new A(a1346.get());

        A a1349 = new A(a1347.get());
        A a1350 = new A(a1348.get());

        A a1351 = new A(a1349.get());
        A a1352 = new A(a1350.get());

        A a1353 = new A(a1351.get());
        A a1354 = new A(a1352.get());

        A a1355 = new A(a1353.get());
        A a1356 = new A(a1354.get());

        A a1357 = new A(a1355.get());
        A a1358 = new A(a1356.get());

        A a1359 = new A(a1357.get());
        A a1360 = new A(a1358.get());

        A a1361 = new A(a1359.get());
        A a1362 = new A(a1360.get());

        A a1363 = new A(a1361.get());
        A a1364 = new A(a1362.get());

        A a1365 = new A(a1363.get());
        A a1366 = new A(a1364.get());

        A a1367 = new A(a1365.get());
        A a1368 = new A(a1366.get());

        A a1369 = new A(a1367.get());
        A a1370 = new A(a1368.get());

        A a1371 = new A(a1369.get());
        A a1372 = new A(a1370.get());

        A a1373 = new A(a1371.get());
        A a1374 = new A(a1372.get());

        A a1375 = new A(a1373.get());
        A a1376 = new A(a1374.get());

        A a1377 = new A(a1375.get());
        A a1378 = new A(a1376.get());

        A a1379 = new A(a1377.get());
        A a1380 = new A(a1378.get());

        A a1381 = new A(a1379.get());
        A a1382 = new A(a1380.get());

        A a1383 = new A(a1381.get());
        A a1384 = new A(a1382.get());

        A a1385 = new A(a1383.get());
        A a1386 = new A(a1384.get());

        A a1387 = new A(a1385.get());
        A a1388 = new A(a1386.get());

        A a1389 = new A(a1387.get());
        A a1390 = new A(a1388.get());

        A a1391 = new A(a1389.get());
        A a1392 = new A(a1390.get());

        A a1393 = new A(a1391.get());
        A a1394 = new A(a1392.get());

        A a1395 = new A(a1393.get());
        A a1396 = new A(a1394.get());

        A a1397 = new A(a1395.get());
        A a1398 = new A(a1396.get());

        A a1399 = new A(a1397.get());
        A a1400 = new A(a1398.get());

        A a1401 = new A(a1399.get());
        A a1402 = new A(a1400.get());

        A a1403 = new A(a1401.get());
        A a1404 = new A(a1402.get());

        A a1405 = new A(a1403.get());
        A a1406 = new A(a1404.get());

        A a1407 = new A(a1405.get());
        A a1408 = new A(a1406.get());

        A a1409 = new A(a1407.get());
        A a1410 = new A(a1408.get());

        A a1411 = new A(a1409.get());
        A a1412 = new A(a1410.get());

        A a1413 = new A(a1411.get());
        A a1414 = new A(a1412.get());

        A a1415 = new A(a1413.get());
        A a1416 = new A(a1414.get());

        A a1417 = new A(a1415.get());
        A a1418 = new A(a1416.get());

        A a1419 = new A(a1417.get());
        A a1420 = new A(a1418.get());

        A a1421 = new A(a1419.get());
        A a1422 = new A(a1420.get());

        A a1423 = new A(a1421.get());
        A a1424 = new A(a1422.get());

        A a1425 = new A(a1423.get());
        A a1426 = new A(a1424.get());

        A a1427 = new A(a1425.get());
        A a1428 = new A(a1426.get());

        A a1429 = new A(a1427.get());
        A a1430 = new A(a1428.get());

        A a1431 = new A(a1429.get());
        A a1432 = new A(a1430.get());

        A a1433 = new A(a1431.get());
        A a1434 = new A(a1432.get());

        A a1435 = new A(a1433.get());
        A a1436 = new A(a1434.get());

        A a1437 = new A(a1435.get());
        A a1438 = new A(a1436.get());

        A a1439 = new A(a1437.get());
        A a1440 = new A(a1438.get());

        A a1441 = new A(a1439.get());
        A a1442 = new A(a1440.get());

        A a1443 = new A(a1441.get());
        A a1444 = new A(a1442.get());

        A a1445 = new A(a1443.get());
        A a1446 = new A(a1444.get());

        A a1447 = new A(a1445.get());
        A a1448 = new A(a1446.get());

        A a1449 = new A(a1447.get());
        A a1450 = new A(a1448.get());

        A a1451 = new A(a1449.get());
        A a1452 = new A(a1450.get());

        A a1453 = new A(a1451.get());
        A a1454 = new A(a1452.get());

        A a1455 = new A(a1453.get());
        A a1456 = new A(a1454.get());

        A a1457 = new A(a1455.get());
        A a1458 = new A(a1456.get());

        A a1459 = new A(a1457.get());
        A a1460 = new A(a1458.get());

        A a1461 = new A(a1459.get());
        A a1462 = new A(a1460.get());

        A a1463 = new A(a1461.get());
        A a1464 = new A(a1462.get());

        A a1465 = new A(a1463.get());
        A a1466 = new A(a1464.get());

        A a1467 = new A(a1465.get());
        A a1468 = new A(a1466.get());

        A a1469 = new A(a1467.get());
        A a1470 = new A(a1468.get());

        A a1471 = new A(a1469.get());
        A a1472 = new A(a1470.get());

        A a1473 = new A(a1471.get());
        A a1474 = new A(a1472.get());

        A a1475 = new A(a1473.get());
        A a1476 = new A(a1474.get());

        A a1477 = new A(a1475.get());
        A a1478 = new A(a1476.get());

        A a1479 = new A(a1477.get());
        A a1480 = new A(a1478.get());

        A a1481 = new A(a1479.get());
        A a1482 = new A(a1480.get());

        A a1483 = new A(a1481.get());
        A a1484 = new A(a1482.get());

        A a1485 = new A(a1483.get());
        A a1486 = new A(a1484.get());

        A a1487 = new A(a1485.get());
        A a1488 = new A(a1486.get());

        A a1489 = new A(a1487.get());
        A a1490 = new A(a1488.get());

        A a1491 = new A(a1489.get());
        A a1492 = new A(a1490.get());

        A a1493 = new A(a1491.get());
        A a1494 = new A(a1492.get());

        A a1495 = new A(a1493.get());
        A a1496 = new A(a1494.get());

        A a1497 = new A(a1495.get());
        A a1498 = new A(a1496.get());

        A a1499 = new A(a1497.get());
        A a1500 = new A(a1498.get());
        return foo3(a1499, a1500);
      }
      public static boolean foo3(A a1499, A a1500) {

        A a1501 = new A(a1499.get());
        A a1502 = new A(a1500.get());

        A a1503 = new A(a1501.get());
        A a1504 = new A(a1502.get());

        A a1505 = new A(a1503.get());
        A a1506 = new A(a1504.get());

        A a1507 = new A(a1505.get());
        A a1508 = new A(a1506.get());

        A a1509 = new A(a1507.get());
        A a1510 = new A(a1508.get());

        A a1511 = new A(a1509.get());
        A a1512 = new A(a1510.get());

        A a1513 = new A(a1511.get());
        A a1514 = new A(a1512.get());

        A a1515 = new A(a1513.get());
        A a1516 = new A(a1514.get());

        A a1517 = new A(a1515.get());
        A a1518 = new A(a1516.get());

        A a1519 = new A(a1517.get());
        A a1520 = new A(a1518.get());

        A a1521 = new A(a1519.get());
        A a1522 = new A(a1520.get());

        A a1523 = new A(a1521.get());
        A a1524 = new A(a1522.get());

        A a1525 = new A(a1523.get());
        A a1526 = new A(a1524.get());

        A a1527 = new A(a1525.get());
        A a1528 = new A(a1526.get());

        A a1529 = new A(a1527.get());
        A a1530 = new A(a1528.get());

        A a1531 = new A(a1529.get());
        A a1532 = new A(a1530.get());

        A a1533 = new A(a1531.get());
        A a1534 = new A(a1532.get());

        A a1535 = new A(a1533.get());
        A a1536 = new A(a1534.get());

        A a1537 = new A(a1535.get());
        A a1538 = new A(a1536.get());

        A a1539 = new A(a1537.get());
        A a1540 = new A(a1538.get());

        A a1541 = new A(a1539.get());
        A a1542 = new A(a1540.get());

        A a1543 = new A(a1541.get());
        A a1544 = new A(a1542.get());

        A a1545 = new A(a1543.get());
        A a1546 = new A(a1544.get());

        A a1547 = new A(a1545.get());
        A a1548 = new A(a1546.get());

        A a1549 = new A(a1547.get());
        A a1550 = new A(a1548.get());

        A a1551 = new A(a1549.get());
        A a1552 = new A(a1550.get());

        A a1553 = new A(a1551.get());
        A a1554 = new A(a1552.get());

        A a1555 = new A(a1553.get());
        A a1556 = new A(a1554.get());

        A a1557 = new A(a1555.get());
        A a1558 = new A(a1556.get());

        A a1559 = new A(a1557.get());
        A a1560 = new A(a1558.get());

        A a1561 = new A(a1559.get());
        A a1562 = new A(a1560.get());

        A a1563 = new A(a1561.get());
        A a1564 = new A(a1562.get());

        A a1565 = new A(a1563.get());
        A a1566 = new A(a1564.get());

        A a1567 = new A(a1565.get());
        A a1568 = new A(a1566.get());

        A a1569 = new A(a1567.get());
        A a1570 = new A(a1568.get());

        A a1571 = new A(a1569.get());
        A a1572 = new A(a1570.get());

        A a1573 = new A(a1571.get());
        A a1574 = new A(a1572.get());

        A a1575 = new A(a1573.get());
        A a1576 = new A(a1574.get());

        A a1577 = new A(a1575.get());
        A a1578 = new A(a1576.get());

        A a1579 = new A(a1577.get());
        A a1580 = new A(a1578.get());

        A a1581 = new A(a1579.get());
        A a1582 = new A(a1580.get());

        A a1583 = new A(a1581.get());
        A a1584 = new A(a1582.get());

        A a1585 = new A(a1583.get());
        A a1586 = new A(a1584.get());

        A a1587 = new A(a1585.get());
        A a1588 = new A(a1586.get());

        A a1589 = new A(a1587.get());
        A a1590 = new A(a1588.get());

        A a1591 = new A(a1589.get());
        A a1592 = new A(a1590.get());

        A a1593 = new A(a1591.get());
        A a1594 = new A(a1592.get());

        A a1595 = new A(a1593.get());
        A a1596 = new A(a1594.get());

        A a1597 = new A(a1595.get());
        A a1598 = new A(a1596.get());

        A a1599 = new A(a1597.get());
        A a1600 = new A(a1598.get());

        A a1601 = new A(a1599.get());
        A a1602 = new A(a1600.get());

        A a1603 = new A(a1601.get());
        A a1604 = new A(a1602.get());

        A a1605 = new A(a1603.get());
        A a1606 = new A(a1604.get());

        A a1607 = new A(a1605.get());
        A a1608 = new A(a1606.get());

        A a1609 = new A(a1607.get());
        A a1610 = new A(a1608.get());

        A a1611 = new A(a1609.get());
        A a1612 = new A(a1610.get());

        A a1613 = new A(a1611.get());
        A a1614 = new A(a1612.get());

        A a1615 = new A(a1613.get());
        A a1616 = new A(a1614.get());

        A a1617 = new A(a1615.get());
        A a1618 = new A(a1616.get());

        A a1619 = new A(a1617.get());
        A a1620 = new A(a1618.get());

        A a1621 = new A(a1619.get());
        A a1622 = new A(a1620.get());

        A a1623 = new A(a1621.get());
        A a1624 = new A(a1622.get());

        A a1625 = new A(a1623.get());
        A a1626 = new A(a1624.get());

        A a1627 = new A(a1625.get());
        A a1628 = new A(a1626.get());

        A a1629 = new A(a1627.get());
        A a1630 = new A(a1628.get());

        A a1631 = new A(a1629.get());
        A a1632 = new A(a1630.get());

        A a1633 = new A(a1631.get());
        A a1634 = new A(a1632.get());

        A a1635 = new A(a1633.get());
        A a1636 = new A(a1634.get());

        A a1637 = new A(a1635.get());
        A a1638 = new A(a1636.get());

        A a1639 = new A(a1637.get());
        A a1640 = new A(a1638.get());

        A a1641 = new A(a1639.get());
        A a1642 = new A(a1640.get());

        A a1643 = new A(a1641.get());
        A a1644 = new A(a1642.get());

        A a1645 = new A(a1643.get());
        A a1646 = new A(a1644.get());

        A a1647 = new A(a1645.get());
        A a1648 = new A(a1646.get());

        A a1649 = new A(a1647.get());
        A a1650 = new A(a1648.get());

        A a1651 = new A(a1649.get());
        A a1652 = new A(a1650.get());

        A a1653 = new A(a1651.get());
        A a1654 = new A(a1652.get());

        A a1655 = new A(a1653.get());
        A a1656 = new A(a1654.get());

        A a1657 = new A(a1655.get());
        A a1658 = new A(a1656.get());

        A a1659 = new A(a1657.get());
        A a1660 = new A(a1658.get());

        A a1661 = new A(a1659.get());
        A a1662 = new A(a1660.get());

        A a1663 = new A(a1661.get());
        A a1664 = new A(a1662.get());

        A a1665 = new A(a1663.get());
        A a1666 = new A(a1664.get());

        A a1667 = new A(a1665.get());
        A a1668 = new A(a1666.get());

        A a1669 = new A(a1667.get());
        A a1670 = new A(a1668.get());

        A a1671 = new A(a1669.get());
        A a1672 = new A(a1670.get());

        A a1673 = new A(a1671.get());
        A a1674 = new A(a1672.get());

        A a1675 = new A(a1673.get());
        A a1676 = new A(a1674.get());

        A a1677 = new A(a1675.get());
        A a1678 = new A(a1676.get());

        A a1679 = new A(a1677.get());
        A a1680 = new A(a1678.get());

        A a1681 = new A(a1679.get());
        A a1682 = new A(a1680.get());

        A a1683 = new A(a1681.get());
        A a1684 = new A(a1682.get());

        A a1685 = new A(a1683.get());
        A a1686 = new A(a1684.get());

        A a1687 = new A(a1685.get());
        A a1688 = new A(a1686.get());

        A a1689 = new A(a1687.get());
        A a1690 = new A(a1688.get());

        A a1691 = new A(a1689.get());
        A a1692 = new A(a1690.get());

        A a1693 = new A(a1691.get());
        A a1694 = new A(a1692.get());

        A a1695 = new A(a1693.get());
        A a1696 = new A(a1694.get());

        A a1697 = new A(a1695.get());
        A a1698 = new A(a1696.get());

        A a1699 = new A(a1697.get());
        A a1700 = new A(a1698.get());

        A a1701 = new A(a1699.get());
        A a1702 = new A(a1700.get());

        A a1703 = new A(a1701.get());
        A a1704 = new A(a1702.get());

        A a1705 = new A(a1703.get());
        A a1706 = new A(a1704.get());

        A a1707 = new A(a1705.get());
        A a1708 = new A(a1706.get());

        A a1709 = new A(a1707.get());
        A a1710 = new A(a1708.get());

        A a1711 = new A(a1709.get());
        A a1712 = new A(a1710.get());

        A a1713 = new A(a1711.get());
        A a1714 = new A(a1712.get());

        A a1715 = new A(a1713.get());
        A a1716 = new A(a1714.get());

        A a1717 = new A(a1715.get());
        A a1718 = new A(a1716.get());

        A a1719 = new A(a1717.get());
        A a1720 = new A(a1718.get());

        A a1721 = new A(a1719.get());
        A a1722 = new A(a1720.get());

        A a1723 = new A(a1721.get());
        A a1724 = new A(a1722.get());

        A a1725 = new A(a1723.get());
        A a1726 = new A(a1724.get());

        A a1727 = new A(a1725.get());
        A a1728 = new A(a1726.get());

        A a1729 = new A(a1727.get());
        A a1730 = new A(a1728.get());

        A a1731 = new A(a1729.get());
        A a1732 = new A(a1730.get());

        A a1733 = new A(a1731.get());
        A a1734 = new A(a1732.get());

        A a1735 = new A(a1733.get());
        A a1736 = new A(a1734.get());

        A a1737 = new A(a1735.get());
        A a1738 = new A(a1736.get());

        A a1739 = new A(a1737.get());
        A a1740 = new A(a1738.get());

        A a1741 = new A(a1739.get());
        A a1742 = new A(a1740.get());

        A a1743 = new A(a1741.get());
        A a1744 = new A(a1742.get());

        A a1745 = new A(a1743.get());
        A a1746 = new A(a1744.get());

        A a1747 = new A(a1745.get());
        A a1748 = new A(a1746.get());

        A a1749 = new A(a1747.get());
        A a1750 = new A(a1748.get());

        A a1751 = new A(a1749.get());
        A a1752 = new A(a1750.get());

        A a1753 = new A(a1751.get());
        A a1754 = new A(a1752.get());

        A a1755 = new A(a1753.get());
        A a1756 = new A(a1754.get());

        A a1757 = new A(a1755.get());
        A a1758 = new A(a1756.get());

        A a1759 = new A(a1757.get());
        A a1760 = new A(a1758.get());

        A a1761 = new A(a1759.get());
        A a1762 = new A(a1760.get());

        A a1763 = new A(a1761.get());
        A a1764 = new A(a1762.get());

        A a1765 = new A(a1763.get());
        A a1766 = new A(a1764.get());

        A a1767 = new A(a1765.get());
        A a1768 = new A(a1766.get());

        A a1769 = new A(a1767.get());
        A a1770 = new A(a1768.get());

        A a1771 = new A(a1769.get());
        A a1772 = new A(a1770.get());

        A a1773 = new A(a1771.get());
        A a1774 = new A(a1772.get());

        A a1775 = new A(a1773.get());
        A a1776 = new A(a1774.get());

        A a1777 = new A(a1775.get());
        A a1778 = new A(a1776.get());

        A a1779 = new A(a1777.get());
        A a1780 = new A(a1778.get());

        A a1781 = new A(a1779.get());
        A a1782 = new A(a1780.get());

        A a1783 = new A(a1781.get());
        A a1784 = new A(a1782.get());

        A a1785 = new A(a1783.get());
        A a1786 = new A(a1784.get());

        A a1787 = new A(a1785.get());
        A a1788 = new A(a1786.get());

        A a1789 = new A(a1787.get());
        A a1790 = new A(a1788.get());

        A a1791 = new A(a1789.get());
        A a1792 = new A(a1790.get());

        A a1793 = new A(a1791.get());
        A a1794 = new A(a1792.get());

        A a1795 = new A(a1793.get());
        A a1796 = new A(a1794.get());

        A a1797 = new A(a1795.get());
        A a1798 = new A(a1796.get());

        A a1799 = new A(a1797.get());
        A a1800 = new A(a1798.get());

        A a1801 = new A(a1799.get());
        A a1802 = new A(a1800.get());

        A a1803 = new A(a1801.get());
        A a1804 = new A(a1802.get());

        A a1805 = new A(a1803.get());
        A a1806 = new A(a1804.get());

        A a1807 = new A(a1805.get());
        A a1808 = new A(a1806.get());

        A a1809 = new A(a1807.get());
        A a1810 = new A(a1808.get());

        A a1811 = new A(a1809.get());
        A a1812 = new A(a1810.get());

        A a1813 = new A(a1811.get());
        A a1814 = new A(a1812.get());

        A a1815 = new A(a1813.get());
        A a1816 = new A(a1814.get());

        A a1817 = new A(a1815.get());
        A a1818 = new A(a1816.get());

        A a1819 = new A(a1817.get());
        A a1820 = new A(a1818.get());

        A a1821 = new A(a1819.get());
        A a1822 = new A(a1820.get());

        A a1823 = new A(a1821.get());
        A a1824 = new A(a1822.get());

        A a1825 = new A(a1823.get());
        A a1826 = new A(a1824.get());

        A a1827 = new A(a1825.get());
        A a1828 = new A(a1826.get());

        A a1829 = new A(a1827.get());
        A a1830 = new A(a1828.get());

        A a1831 = new A(a1829.get());
        A a1832 = new A(a1830.get());

        A a1833 = new A(a1831.get());
        A a1834 = new A(a1832.get());

        A a1835 = new A(a1833.get());
        A a1836 = new A(a1834.get());

        A a1837 = new A(a1835.get());
        A a1838 = new A(a1836.get());

        A a1839 = new A(a1837.get());
        A a1840 = new A(a1838.get());

        A a1841 = new A(a1839.get());
        A a1842 = new A(a1840.get());

        A a1843 = new A(a1841.get());
        A a1844 = new A(a1842.get());

        A a1845 = new A(a1843.get());
        A a1846 = new A(a1844.get());

        A a1847 = new A(a1845.get());
        A a1848 = new A(a1846.get());

        A a1849 = new A(a1847.get());
        A a1850 = new A(a1848.get());

        A a1851 = new A(a1849.get());
        A a1852 = new A(a1850.get());

        A a1853 = new A(a1851.get());
        A a1854 = new A(a1852.get());

        A a1855 = new A(a1853.get());
        A a1856 = new A(a1854.get());

        A a1857 = new A(a1855.get());
        A a1858 = new A(a1856.get());

        A a1859 = new A(a1857.get());
        A a1860 = new A(a1858.get());

        A a1861 = new A(a1859.get());
        A a1862 = new A(a1860.get());

        A a1863 = new A(a1861.get());
        A a1864 = new A(a1862.get());

        A a1865 = new A(a1863.get());
        A a1866 = new A(a1864.get());

        A a1867 = new A(a1865.get());
        A a1868 = new A(a1866.get());

        A a1869 = new A(a1867.get());
        A a1870 = new A(a1868.get());

        A a1871 = new A(a1869.get());
        A a1872 = new A(a1870.get());

        A a1873 = new A(a1871.get());
        A a1874 = new A(a1872.get());

        A a1875 = new A(a1873.get());
        A a1876 = new A(a1874.get());

        A a1877 = new A(a1875.get());
        A a1878 = new A(a1876.get());

        A a1879 = new A(a1877.get());
        A a1880 = new A(a1878.get());

        A a1881 = new A(a1879.get());
        A a1882 = new A(a1880.get());

        A a1883 = new A(a1881.get());
        A a1884 = new A(a1882.get());

        A a1885 = new A(a1883.get());
        A a1886 = new A(a1884.get());

        A a1887 = new A(a1885.get());
        A a1888 = new A(a1886.get());

        A a1889 = new A(a1887.get());
        A a1890 = new A(a1888.get());

        A a1891 = new A(a1889.get());
        A a1892 = new A(a1890.get());

        A a1893 = new A(a1891.get());
        A a1894 = new A(a1892.get());

        A a1895 = new A(a1893.get());
        A a1896 = new A(a1894.get());

        A a1897 = new A(a1895.get());
        A a1898 = new A(a1896.get());

        A a1899 = new A(a1897.get());
        A a1900 = new A(a1898.get());

        A a1901 = new A(a1899.get());
        A a1902 = new A(a1900.get());

        A a1903 = new A(a1901.get());
        A a1904 = new A(a1902.get());

        A a1905 = new A(a1903.get());
        A a1906 = new A(a1904.get());

        A a1907 = new A(a1905.get());
        A a1908 = new A(a1906.get());

        A a1909 = new A(a1907.get());
        A a1910 = new A(a1908.get());

        A a1911 = new A(a1909.get());
        A a1912 = new A(a1910.get());

        A a1913 = new A(a1911.get());
        A a1914 = new A(a1912.get());

        A a1915 = new A(a1913.get());
        A a1916 = new A(a1914.get());

        A a1917 = new A(a1915.get());
        A a1918 = new A(a1916.get());

        A a1919 = new A(a1917.get());
        A a1920 = new A(a1918.get());

        A a1921 = new A(a1919.get());
        A a1922 = new A(a1920.get());

        A a1923 = new A(a1921.get());
        A a1924 = new A(a1922.get());

        A a1925 = new A(a1923.get());
        A a1926 = new A(a1924.get());

        A a1927 = new A(a1925.get());
        A a1928 = new A(a1926.get());

        A a1929 = new A(a1927.get());
        A a1930 = new A(a1928.get());

        A a1931 = new A(a1929.get());
        A a1932 = new A(a1930.get());

        A a1933 = new A(a1931.get());
        A a1934 = new A(a1932.get());

        A a1935 = new A(a1933.get());
        A a1936 = new A(a1934.get());

        A a1937 = new A(a1935.get());
        A a1938 = new A(a1936.get());

        A a1939 = new A(a1937.get());
        A a1940 = new A(a1938.get());

        A a1941 = new A(a1939.get());
        A a1942 = new A(a1940.get());

        A a1943 = new A(a1941.get());
        A a1944 = new A(a1942.get());

        A a1945 = new A(a1943.get());
        A a1946 = new A(a1944.get());

        A a1947 = new A(a1945.get());
        A a1948 = new A(a1946.get());

        A a1949 = new A(a1947.get());
        A a1950 = new A(a1948.get());

        A a1951 = new A(a1949.get());
        A a1952 = new A(a1950.get());

        A a1953 = new A(a1951.get());
        A a1954 = new A(a1952.get());

        A a1955 = new A(a1953.get());
        A a1956 = new A(a1954.get());

        A a1957 = new A(a1955.get());
        A a1958 = new A(a1956.get());

        A a1959 = new A(a1957.get());
        A a1960 = new A(a1958.get());

        A a1961 = new A(a1959.get());
        A a1962 = new A(a1960.get());

        A a1963 = new A(a1961.get());
        A a1964 = new A(a1962.get());

        A a1965 = new A(a1963.get());
        A a1966 = new A(a1964.get());

        A a1967 = new A(a1965.get());
        A a1968 = new A(a1966.get());

        A a1969 = new A(a1967.get());
        A a1970 = new A(a1968.get());

        A a1971 = new A(a1969.get());
        A a1972 = new A(a1970.get());

        A a1973 = new A(a1971.get());
        A a1974 = new A(a1972.get());

        A a1975 = new A(a1973.get());
        A a1976 = new A(a1974.get());

        A a1977 = new A(a1975.get());
        A a1978 = new A(a1976.get());

        A a1979 = new A(a1977.get());
        A a1980 = new A(a1978.get());

        A a1981 = new A(a1979.get());
        A a1982 = new A(a1980.get());

        A a1983 = new A(a1981.get());
        A a1984 = new A(a1982.get());

        A a1985 = new A(a1983.get());
        A a1986 = new A(a1984.get());

        A a1987 = new A(a1985.get());
        A a1988 = new A(a1986.get());

        A a1989 = new A(a1987.get());
        A a1990 = new A(a1988.get());

        A a1991 = new A(a1989.get());
        A a1992 = new A(a1990.get());

        A a1993 = new A(a1991.get());
        A a1994 = new A(a1992.get());

        A a1995 = new A(a1993.get());
        A a1996 = new A(a1994.get());

        A a1997 = new A(a1995.get());
        A a1998 = new A(a1996.get());

        A a1999 = new A(a1997.get());
        A a2000 = new A(a1998.get());

        return foo4(a1999, a2000);
      }
        
      public static boolean foo4(A a1999, A a2000) {
    	  
        A a2001 = new A(a1999.get());
        A a2002 = new A(a2000.get());

        A a2003 = new A(a2001.get());
        A a2004 = new A(a2002.get());

        A a2005 = new A(a2003.get());
        A a2006 = new A(a2004.get());

        A a2007 = new A(a2005.get());
        A a2008 = new A(a2006.get());

        A a2009 = new A(a2007.get());
        A a2010 = new A(a2008.get());

        A a2011 = new A(a2009.get());
        A a2012 = new A(a2010.get());

        A a2013 = new A(a2011.get());
        A a2014 = new A(a2012.get());

        A a2015 = new A(a2013.get());
        A a2016 = new A(a2014.get());

        A a2017 = new A(a2015.get());
        A a2018 = new A(a2016.get());

        A a2019 = new A(a2017.get());
        A a2020 = new A(a2018.get());

        A a2021 = new A(a2019.get());
        A a2022 = new A(a2020.get());

        A a2023 = new A(a2021.get());
        A a2024 = new A(a2022.get());

        A a2025 = new A(a2023.get());
        A a2026 = new A(a2024.get());

        A a2027 = new A(a2025.get());
        A a2028 = new A(a2026.get());

        A a2029 = new A(a2027.get());
        A a2030 = new A(a2028.get());

        A a2031 = new A(a2029.get());
        A a2032 = new A(a2030.get());

        A a2033 = new A(a2031.get());
        A a2034 = new A(a2032.get());

        A a2035 = new A(a2033.get());
        A a2036 = new A(a2034.get());

        A a2037 = new A(a2035.get());
        A a2038 = new A(a2036.get());

        A a2039 = new A(a2037.get());
        A a2040 = new A(a2038.get());

        A a2041 = new A(a2039.get());
        A a2042 = new A(a2040.get());

        A a2043 = new A(a2041.get());
        A a2044 = new A(a2042.get());

        A a2045 = new A(a2043.get());
        A a2046 = new A(a2044.get());

        A a2047 = new A(a2045.get());
        A a2048 = new A(a2046.get());

        A a2049 = new A(a2047.get());
        A a2050 = new A(a2048.get());

        A a2051 = new A(a2049.get());
        A a2052 = new A(a2050.get());

        A a2053 = new A(a2051.get());
        A a2054 = new A(a2052.get());

        A a2055 = new A(a2053.get());
        A a2056 = new A(a2054.get());

        A a2057 = new A(a2055.get());
        A a2058 = new A(a2056.get());

        A a2059 = new A(a2057.get());
        A a2060 = new A(a2058.get());

        A a2061 = new A(a2059.get());
        A a2062 = new A(a2060.get());

        A a2063 = new A(a2061.get());
        A a2064 = new A(a2062.get());

        A a2065 = new A(a2063.get());
        A a2066 = new A(a2064.get());

        A a2067 = new A(a2065.get());
        A a2068 = new A(a2066.get());

        A a2069 = new A(a2067.get());
        A a2070 = new A(a2068.get());

        A a2071 = new A(a2069.get());
        A a2072 = new A(a2070.get());

        A a2073 = new A(a2071.get());
        A a2074 = new A(a2072.get());

        A a2075 = new A(a2073.get());
        A a2076 = new A(a2074.get());

        A a2077 = new A(a2075.get());
        A a2078 = new A(a2076.get());

        A a2079 = new A(a2077.get());
        A a2080 = new A(a2078.get());

        A a2081 = new A(a2079.get());
        A a2082 = new A(a2080.get());

        A a2083 = new A(a2081.get());
        A a2084 = new A(a2082.get());

        A a2085 = new A(a2083.get());
        A a2086 = new A(a2084.get());

        A a2087 = new A(a2085.get());
        A a2088 = new A(a2086.get());

        A a2089 = new A(a2087.get());
        A a2090 = new A(a2088.get());

        A a2091 = new A(a2089.get());
        A a2092 = new A(a2090.get());

        A a2093 = new A(a2091.get());
        A a2094 = new A(a2092.get());

        A a2095 = new A(a2093.get());
        A a2096 = new A(a2094.get());

        A a2097 = new A(a2095.get());
        A a2098 = new A(a2096.get());

        A a2099 = new A(a2097.get());
        A a2100 = new A(a2098.get());

        A a2101 = new A(a2099.get());
        A a2102 = new A(a2100.get());

        A a2103 = new A(a2101.get());
        A a2104 = new A(a2102.get());

        A a2105 = new A(a2103.get());
        A a2106 = new A(a2104.get());

        A a2107 = new A(a2105.get());
        A a2108 = new A(a2106.get());

        A a2109 = new A(a2107.get());
        A a2110 = new A(a2108.get());

        A a2111 = new A(a2109.get());
        A a2112 = new A(a2110.get());

        A a2113 = new A(a2111.get());
        A a2114 = new A(a2112.get());

        A a2115 = new A(a2113.get());
        A a2116 = new A(a2114.get());

        A a2117 = new A(a2115.get());
        A a2118 = new A(a2116.get());

        A a2119 = new A(a2117.get());
        A a2120 = new A(a2118.get());

        A a2121 = new A(a2119.get());
        A a2122 = new A(a2120.get());

        A a2123 = new A(a2121.get());
        A a2124 = new A(a2122.get());

        A a2125 = new A(a2123.get());
        A a2126 = new A(a2124.get());

        A a2127 = new A(a2125.get());
        A a2128 = new A(a2126.get());

        A a2129 = new A(a2127.get());
        A a2130 = new A(a2128.get());

        A a2131 = new A(a2129.get());
        A a2132 = new A(a2130.get());

        A a2133 = new A(a2131.get());
        A a2134 = new A(a2132.get());

        A a2135 = new A(a2133.get());
        A a2136 = new A(a2134.get());

        A a2137 = new A(a2135.get());
        A a2138 = new A(a2136.get());

        A a2139 = new A(a2137.get());
        A a2140 = new A(a2138.get());

        A a2141 = new A(a2139.get());
        A a2142 = new A(a2140.get());

        A a2143 = new A(a2141.get());
        A a2144 = new A(a2142.get());

        A a2145 = new A(a2143.get());
        A a2146 = new A(a2144.get());

        A a2147 = new A(a2145.get());
        A a2148 = new A(a2146.get());

        A a2149 = new A(a2147.get());
        A a2150 = new A(a2148.get());

        A a2151 = new A(a2149.get());
        A a2152 = new A(a2150.get());

        A a2153 = new A(a2151.get());
        A a2154 = new A(a2152.get());

        A a2155 = new A(a2153.get());
        A a2156 = new A(a2154.get());

        A a2157 = new A(a2155.get());
        A a2158 = new A(a2156.get());

        A a2159 = new A(a2157.get());
        A a2160 = new A(a2158.get());

        A a2161 = new A(a2159.get());
        A a2162 = new A(a2160.get());

        A a2163 = new A(a2161.get());
        A a2164 = new A(a2162.get());

        A a2165 = new A(a2163.get());
        A a2166 = new A(a2164.get());

        A a2167 = new A(a2165.get());
        A a2168 = new A(a2166.get());

        A a2169 = new A(a2167.get());
        A a2170 = new A(a2168.get());

        A a2171 = new A(a2169.get());
        A a2172 = new A(a2170.get());

        A a2173 = new A(a2171.get());
        A a2174 = new A(a2172.get());

        A a2175 = new A(a2173.get());
        A a2176 = new A(a2174.get());

        A a2177 = new A(a2175.get());
        A a2178 = new A(a2176.get());

        A a2179 = new A(a2177.get());
        A a2180 = new A(a2178.get());

        A a2181 = new A(a2179.get());
        A a2182 = new A(a2180.get());

        A a2183 = new A(a2181.get());
        A a2184 = new A(a2182.get());

        A a2185 = new A(a2183.get());
        A a2186 = new A(a2184.get());

        A a2187 = new A(a2185.get());
        A a2188 = new A(a2186.get());

        A a2189 = new A(a2187.get());
        A a2190 = new A(a2188.get());

        A a2191 = new A(a2189.get());
        A a2192 = new A(a2190.get());

        A a2193 = new A(a2191.get());
        A a2194 = new A(a2192.get());

        A a2195 = new A(a2193.get());
        A a2196 = new A(a2194.get());

        A a2197 = new A(a2195.get());
        A a2198 = new A(a2196.get());

        A a2199 = new A(a2197.get());
        A a2200 = new A(a2198.get());

        A a2201 = new A(a2199.get());
        A a2202 = new A(a2200.get());

        A a2203 = new A(a2201.get());
        A a2204 = new A(a2202.get());

        A a2205 = new A(a2203.get());
        A a2206 = new A(a2204.get());

        A a2207 = new A(a2205.get());
        A a2208 = new A(a2206.get());

        A a2209 = new A(a2207.get());
        A a2210 = new A(a2208.get());

        A a2211 = new A(a2209.get());
        A a2212 = new A(a2210.get());

        A a2213 = new A(a2211.get());
        A a2214 = new A(a2212.get());

        A a2215 = new A(a2213.get());
        A a2216 = new A(a2214.get());

        A a2217 = new A(a2215.get());
        A a2218 = new A(a2216.get());

        A a2219 = new A(a2217.get());
        A a2220 = new A(a2218.get());

        A a2221 = new A(a2219.get());
        A a2222 = new A(a2220.get());

        A a2223 = new A(a2221.get());
        A a2224 = new A(a2222.get());

        A a2225 = new A(a2223.get());
        A a2226 = new A(a2224.get());

        A a2227 = new A(a2225.get());
        A a2228 = new A(a2226.get());

        A a2229 = new A(a2227.get());
        A a2230 = new A(a2228.get());

        A a2231 = new A(a2229.get());
        A a2232 = new A(a2230.get());

        A a2233 = new A(a2231.get());
        A a2234 = new A(a2232.get());

        A a2235 = new A(a2233.get());
        A a2236 = new A(a2234.get());

        A a2237 = new A(a2235.get());
        A a2238 = new A(a2236.get());

        A a2239 = new A(a2237.get());
        A a2240 = new A(a2238.get());

        A a2241 = new A(a2239.get());
        A a2242 = new A(a2240.get());

        A a2243 = new A(a2241.get());
        A a2244 = new A(a2242.get());

        A a2245 = new A(a2243.get());
        A a2246 = new A(a2244.get());

        A a2247 = new A(a2245.get());
        A a2248 = new A(a2246.get());

        A a2249 = new A(a2247.get());
        A a2250 = new A(a2248.get());

        A a2251 = new A(a2249.get());
        A a2252 = new A(a2250.get());

        A a2253 = new A(a2251.get());
        A a2254 = new A(a2252.get());

        A a2255 = new A(a2253.get());
        A a2256 = new A(a2254.get());

        A a2257 = new A(a2255.get());
        A a2258 = new A(a2256.get());

        A a2259 = new A(a2257.get());
        A a2260 = new A(a2258.get());

        A a2261 = new A(a2259.get());
        A a2262 = new A(a2260.get());

        A a2263 = new A(a2261.get());
        A a2264 = new A(a2262.get());

        A a2265 = new A(a2263.get());
        A a2266 = new A(a2264.get());

        A a2267 = new A(a2265.get());
        A a2268 = new A(a2266.get());

        A a2269 = new A(a2267.get());
        A a2270 = new A(a2268.get());

        A a2271 = new A(a2269.get());
        A a2272 = new A(a2270.get());

        A a2273 = new A(a2271.get());
        A a2274 = new A(a2272.get());

        A a2275 = new A(a2273.get());
        A a2276 = new A(a2274.get());

        A a2277 = new A(a2275.get());
        A a2278 = new A(a2276.get());

        A a2279 = new A(a2277.get());
        A a2280 = new A(a2278.get());

        A a2281 = new A(a2279.get());
        A a2282 = new A(a2280.get());

        A a2283 = new A(a2281.get());
        A a2284 = new A(a2282.get());

        A a2285 = new A(a2283.get());
        A a2286 = new A(a2284.get());

        A a2287 = new A(a2285.get());
        A a2288 = new A(a2286.get());

        A a2289 = new A(a2287.get());
        A a2290 = new A(a2288.get());

        A a2291 = new A(a2289.get());
        A a2292 = new A(a2290.get());

        A a2293 = new A(a2291.get());
        A a2294 = new A(a2292.get());

        A a2295 = new A(a2293.get());
        A a2296 = new A(a2294.get());

        A a2297 = new A(a2295.get());
        A a2298 = new A(a2296.get());

        A a2299 = new A(a2297.get());
        A a2300 = new A(a2298.get());

        A a2301 = new A(a2299.get());
        A a2302 = new A(a2300.get());

        A a2303 = new A(a2301.get());
        A a2304 = new A(a2302.get());

        A a2305 = new A(a2303.get());
        A a2306 = new A(a2304.get());

        A a2307 = new A(a2305.get());
        A a2308 = new A(a2306.get());

        A a2309 = new A(a2307.get());
        A a2310 = new A(a2308.get());

        A a2311 = new A(a2309.get());
        A a2312 = new A(a2310.get());

        A a2313 = new A(a2311.get());
        A a2314 = new A(a2312.get());

        A a2315 = new A(a2313.get());
        A a2316 = new A(a2314.get());

        A a2317 = new A(a2315.get());
        A a2318 = new A(a2316.get());

        A a2319 = new A(a2317.get());
        A a2320 = new A(a2318.get());

        A a2321 = new A(a2319.get());
        A a2322 = new A(a2320.get());

        A a2323 = new A(a2321.get());
        A a2324 = new A(a2322.get());

        A a2325 = new A(a2323.get());
        A a2326 = new A(a2324.get());

        A a2327 = new A(a2325.get());
        A a2328 = new A(a2326.get());

        A a2329 = new A(a2327.get());
        A a2330 = new A(a2328.get());

        A a2331 = new A(a2329.get());
        A a2332 = new A(a2330.get());

        A a2333 = new A(a2331.get());
        A a2334 = new A(a2332.get());

        A a2335 = new A(a2333.get());
        A a2336 = new A(a2334.get());

        A a2337 = new A(a2335.get());
        A a2338 = new A(a2336.get());

        A a2339 = new A(a2337.get());
        A a2340 = new A(a2338.get());

        A a2341 = new A(a2339.get());
        A a2342 = new A(a2340.get());

        A a2343 = new A(a2341.get());
        A a2344 = new A(a2342.get());

        A a2345 = new A(a2343.get());
        A a2346 = new A(a2344.get());

        A a2347 = new A(a2345.get());
        A a2348 = new A(a2346.get());

        A a2349 = new A(a2347.get());
        A a2350 = new A(a2348.get());

        A a2351 = new A(a2349.get());
        A a2352 = new A(a2350.get());

        A a2353 = new A(a2351.get());
        A a2354 = new A(a2352.get());

        A a2355 = new A(a2353.get());
        A a2356 = new A(a2354.get());

        A a2357 = new A(a2355.get());
        A a2358 = new A(a2356.get());

        A a2359 = new A(a2357.get());
        A a2360 = new A(a2358.get());

        A a2361 = new A(a2359.get());
        A a2362 = new A(a2360.get());

        A a2363 = new A(a2361.get());
        A a2364 = new A(a2362.get());

        A a2365 = new A(a2363.get());
        A a2366 = new A(a2364.get());

        A a2367 = new A(a2365.get());
        A a2368 = new A(a2366.get());

        A a2369 = new A(a2367.get());
        A a2370 = new A(a2368.get());

        A a2371 = new A(a2369.get());
        A a2372 = new A(a2370.get());

        A a2373 = new A(a2371.get());
        A a2374 = new A(a2372.get());

        A a2375 = new A(a2373.get());
        A a2376 = new A(a2374.get());

        A a2377 = new A(a2375.get());
        A a2378 = new A(a2376.get());

        A a2379 = new A(a2377.get());
        A a2380 = new A(a2378.get());

        A a2381 = new A(a2379.get());
        A a2382 = new A(a2380.get());

        A a2383 = new A(a2381.get());
        A a2384 = new A(a2382.get());

        A a2385 = new A(a2383.get());
        A a2386 = new A(a2384.get());

        A a2387 = new A(a2385.get());
        A a2388 = new A(a2386.get());

        A a2389 = new A(a2387.get());
        A a2390 = new A(a2388.get());

        A a2391 = new A(a2389.get());
        A a2392 = new A(a2390.get());

        A a2393 = new A(a2391.get());
        A a2394 = new A(a2392.get());

        A a2395 = new A(a2393.get());
        A a2396 = new A(a2394.get());

        A a2397 = new A(a2395.get());
        A a2398 = new A(a2396.get());

        A a2399 = new A(a2397.get());
        A a2400 = new A(a2398.get());

        A a2401 = new A(a2399.get());
        A a2402 = new A(a2400.get());

        A a2403 = new A(a2401.get());
        A a2404 = new A(a2402.get());

        A a2405 = new A(a2403.get());
        A a2406 = new A(a2404.get());

        A a2407 = new A(a2405.get());
        A a2408 = new A(a2406.get());

        A a2409 = new A(a2407.get());
        A a2410 = new A(a2408.get());

        A a2411 = new A(a2409.get());
        A a2412 = new A(a2410.get());

        A a2413 = new A(a2411.get());
        A a2414 = new A(a2412.get());

        A a2415 = new A(a2413.get());
        A a2416 = new A(a2414.get());

        A a2417 = new A(a2415.get());
        A a2418 = new A(a2416.get());

        A a2419 = new A(a2417.get());
        A a2420 = new A(a2418.get());

        A a2421 = new A(a2419.get());
        A a2422 = new A(a2420.get());

        A a2423 = new A(a2421.get());
        A a2424 = new A(a2422.get());

        A a2425 = new A(a2423.get());
        A a2426 = new A(a2424.get());

        A a2427 = new A(a2425.get());
        A a2428 = new A(a2426.get());

        A a2429 = new A(a2427.get());
        A a2430 = new A(a2428.get());

        A a2431 = new A(a2429.get());
        A a2432 = new A(a2430.get());

        A a2433 = new A(a2431.get());
        A a2434 = new A(a2432.get());

        A a2435 = new A(a2433.get());
        A a2436 = new A(a2434.get());

        A a2437 = new A(a2435.get());
        A a2438 = new A(a2436.get());

        A a2439 = new A(a2437.get());
        A a2440 = new A(a2438.get());

        A a2441 = new A(a2439.get());
        A a2442 = new A(a2440.get());

        A a2443 = new A(a2441.get());
        A a2444 = new A(a2442.get());

        A a2445 = new A(a2443.get());
        A a2446 = new A(a2444.get());

        A a2447 = new A(a2445.get());
        A a2448 = new A(a2446.get());

        A a2449 = new A(a2447.get());
        A a2450 = new A(a2448.get());

        A a2451 = new A(a2449.get());
        A a2452 = new A(a2450.get());

        A a2453 = new A(a2451.get());
        A a2454 = new A(a2452.get());

        A a2455 = new A(a2453.get());
        A a2456 = new A(a2454.get());

        A a2457 = new A(a2455.get());
        A a2458 = new A(a2456.get());

        A a2459 = new A(a2457.get());
        A a2460 = new A(a2458.get());

        A a2461 = new A(a2459.get());
        A a2462 = new A(a2460.get());

        A a2463 = new A(a2461.get());
        A a2464 = new A(a2462.get());

        A a2465 = new A(a2463.get());
        A a2466 = new A(a2464.get());

        A a2467 = new A(a2465.get());
        A a2468 = new A(a2466.get());

        A a2469 = new A(a2467.get());
        A a2470 = new A(a2468.get());

        A a2471 = new A(a2469.get());
        A a2472 = new A(a2470.get());

        A a2473 = new A(a2471.get());
        A a2474 = new A(a2472.get());

        A a2475 = new A(a2473.get());
        A a2476 = new A(a2474.get());

        A a2477 = new A(a2475.get());
        A a2478 = new A(a2476.get());

        A a2479 = new A(a2477.get());
        A a2480 = new A(a2478.get());

        A a2481 = new A(a2479.get());
        A a2482 = new A(a2480.get());

        A a2483 = new A(a2481.get());
        A a2484 = new A(a2482.get());

        A a2485 = new A(a2483.get());
        A a2486 = new A(a2484.get());

        A a2487 = new A(a2485.get());
        A a2488 = new A(a2486.get());

        A a2489 = new A(a2487.get());
        A a2490 = new A(a2488.get());

        A a2491 = new A(a2489.get());
        A a2492 = new A(a2490.get());

        A a2493 = new A(a2491.get());
        A a2494 = new A(a2492.get());

        A a2495 = new A(a2493.get());
        A a2496 = new A(a2494.get());

        A a2497 = new A(a2495.get());
        A a2498 = new A(a2496.get());

        A a2499 = new A(a2497.get());
        A a2500 = new A(a2498.get());

        return foo5(a2499, a2500);
      }
      public static boolean foo5(A a2499, A a2500) {
        A a2501 = new A(a2499.get());
        A a2502 = new A(a2500.get());

        A a2503 = new A(a2501.get());
        A a2504 = new A(a2502.get());

        A a2505 = new A(a2503.get());
        A a2506 = new A(a2504.get());

        A a2507 = new A(a2505.get());
        A a2508 = new A(a2506.get());

        A a2509 = new A(a2507.get());
        A a2510 = new A(a2508.get());

        A a2511 = new A(a2509.get());
        A a2512 = new A(a2510.get());

        A a2513 = new A(a2511.get());
        A a2514 = new A(a2512.get());

        A a2515 = new A(a2513.get());
        A a2516 = new A(a2514.get());

        A a2517 = new A(a2515.get());
        A a2518 = new A(a2516.get());

        A a2519 = new A(a2517.get());
        A a2520 = new A(a2518.get());

        A a2521 = new A(a2519.get());
        A a2522 = new A(a2520.get());

        A a2523 = new A(a2521.get());
        A a2524 = new A(a2522.get());

        A a2525 = new A(a2523.get());
        A a2526 = new A(a2524.get());

        A a2527 = new A(a2525.get());
        A a2528 = new A(a2526.get());

        A a2529 = new A(a2527.get());
        A a2530 = new A(a2528.get());

        A a2531 = new A(a2529.get());
        A a2532 = new A(a2530.get());

        A a2533 = new A(a2531.get());
        A a2534 = new A(a2532.get());

        A a2535 = new A(a2533.get());
        A a2536 = new A(a2534.get());

        A a2537 = new A(a2535.get());
        A a2538 = new A(a2536.get());

        A a2539 = new A(a2537.get());
        A a2540 = new A(a2538.get());

        A a2541 = new A(a2539.get());
        A a2542 = new A(a2540.get());

        A a2543 = new A(a2541.get());
        A a2544 = new A(a2542.get());

        A a2545 = new A(a2543.get());
        A a2546 = new A(a2544.get());

        A a2547 = new A(a2545.get());
        A a2548 = new A(a2546.get());

        A a2549 = new A(a2547.get());
        A a2550 = new A(a2548.get());

        A a2551 = new A(a2549.get());
        A a2552 = new A(a2550.get());

        A a2553 = new A(a2551.get());
        A a2554 = new A(a2552.get());

        A a2555 = new A(a2553.get());
        A a2556 = new A(a2554.get());

        A a2557 = new A(a2555.get());
        A a2558 = new A(a2556.get());

        A a2559 = new A(a2557.get());
        A a2560 = new A(a2558.get());

        A a2561 = new A(a2559.get());
        A a2562 = new A(a2560.get());

        A a2563 = new A(a2561.get());
        A a2564 = new A(a2562.get());

        A a2565 = new A(a2563.get());
        A a2566 = new A(a2564.get());

        A a2567 = new A(a2565.get());
        A a2568 = new A(a2566.get());

        A a2569 = new A(a2567.get());
        A a2570 = new A(a2568.get());

        A a2571 = new A(a2569.get());
        A a2572 = new A(a2570.get());

        A a2573 = new A(a2571.get());
        A a2574 = new A(a2572.get());

        A a2575 = new A(a2573.get());
        A a2576 = new A(a2574.get());

        A a2577 = new A(a2575.get());
        A a2578 = new A(a2576.get());

        A a2579 = new A(a2577.get());
        A a2580 = new A(a2578.get());

        A a2581 = new A(a2579.get());
        A a2582 = new A(a2580.get());

        A a2583 = new A(a2581.get());
        A a2584 = new A(a2582.get());

        A a2585 = new A(a2583.get());
        A a2586 = new A(a2584.get());

        A a2587 = new A(a2585.get());
        A a2588 = new A(a2586.get());

        A a2589 = new A(a2587.get());
        A a2590 = new A(a2588.get());

        A a2591 = new A(a2589.get());
        A a2592 = new A(a2590.get());

        A a2593 = new A(a2591.get());
        A a2594 = new A(a2592.get());

        A a2595 = new A(a2593.get());
        A a2596 = new A(a2594.get());

        A a2597 = new A(a2595.get());
        A a2598 = new A(a2596.get());

        A a2599 = new A(a2597.get());
        A a2600 = new A(a2598.get());

        A a2601 = new A(a2599.get());
        A a2602 = new A(a2600.get());

        A a2603 = new A(a2601.get());
        A a2604 = new A(a2602.get());

        A a2605 = new A(a2603.get());
        A a2606 = new A(a2604.get());

        A a2607 = new A(a2605.get());
        A a2608 = new A(a2606.get());

        A a2609 = new A(a2607.get());
        A a2610 = new A(a2608.get());

        A a2611 = new A(a2609.get());
        A a2612 = new A(a2610.get());

        A a2613 = new A(a2611.get());
        A a2614 = new A(a2612.get());

        A a2615 = new A(a2613.get());
        A a2616 = new A(a2614.get());

        A a2617 = new A(a2615.get());
        A a2618 = new A(a2616.get());

        A a2619 = new A(a2617.get());
        A a2620 = new A(a2618.get());

        A a2621 = new A(a2619.get());
        A a2622 = new A(a2620.get());

        A a2623 = new A(a2621.get());
        A a2624 = new A(a2622.get());

        A a2625 = new A(a2623.get());
        A a2626 = new A(a2624.get());

        A a2627 = new A(a2625.get());
        A a2628 = new A(a2626.get());

        A a2629 = new A(a2627.get());
        A a2630 = new A(a2628.get());

        A a2631 = new A(a2629.get());
        A a2632 = new A(a2630.get());

        A a2633 = new A(a2631.get());
        A a2634 = new A(a2632.get());

        A a2635 = new A(a2633.get());
        A a2636 = new A(a2634.get());

        A a2637 = new A(a2635.get());
        A a2638 = new A(a2636.get());

        A a2639 = new A(a2637.get());
        A a2640 = new A(a2638.get());

        A a2641 = new A(a2639.get());
        A a2642 = new A(a2640.get());

        A a2643 = new A(a2641.get());
        A a2644 = new A(a2642.get());

        A a2645 = new A(a2643.get());
        A a2646 = new A(a2644.get());

        A a2647 = new A(a2645.get());
        A a2648 = new A(a2646.get());

        A a2649 = new A(a2647.get());
        A a2650 = new A(a2648.get());

        A a2651 = new A(a2649.get());
        A a2652 = new A(a2650.get());

        A a2653 = new A(a2651.get());
        A a2654 = new A(a2652.get());

        A a2655 = new A(a2653.get());
        A a2656 = new A(a2654.get());

        A a2657 = new A(a2655.get());
        A a2658 = new A(a2656.get());

        A a2659 = new A(a2657.get());
        A a2660 = new A(a2658.get());

        A a2661 = new A(a2659.get());
        A a2662 = new A(a2660.get());

        A a2663 = new A(a2661.get());
        A a2664 = new A(a2662.get());

        A a2665 = new A(a2663.get());
        A a2666 = new A(a2664.get());

        A a2667 = new A(a2665.get());
        A a2668 = new A(a2666.get());

        A a2669 = new A(a2667.get());
        A a2670 = new A(a2668.get());

        A a2671 = new A(a2669.get());
        A a2672 = new A(a2670.get());

        A a2673 = new A(a2671.get());
        A a2674 = new A(a2672.get());

        A a2675 = new A(a2673.get());
        A a2676 = new A(a2674.get());

        A a2677 = new A(a2675.get());
        A a2678 = new A(a2676.get());

        A a2679 = new A(a2677.get());
        A a2680 = new A(a2678.get());

        A a2681 = new A(a2679.get());
        A a2682 = new A(a2680.get());

        A a2683 = new A(a2681.get());
        A a2684 = new A(a2682.get());

        A a2685 = new A(a2683.get());
        A a2686 = new A(a2684.get());

        A a2687 = new A(a2685.get());
        A a2688 = new A(a2686.get());

        A a2689 = new A(a2687.get());
        A a2690 = new A(a2688.get());

        A a2691 = new A(a2689.get());
        A a2692 = new A(a2690.get());

        A a2693 = new A(a2691.get());
        A a2694 = new A(a2692.get());

        A a2695 = new A(a2693.get());
        A a2696 = new A(a2694.get());

        A a2697 = new A(a2695.get());
        A a2698 = new A(a2696.get());

        A a2699 = new A(a2697.get());
        A a2700 = new A(a2698.get());

        A a2701 = new A(a2699.get());
        A a2702 = new A(a2700.get());

        A a2703 = new A(a2701.get());
        A a2704 = new A(a2702.get());

        A a2705 = new A(a2703.get());
        A a2706 = new A(a2704.get());

        A a2707 = new A(a2705.get());
        A a2708 = new A(a2706.get());

        A a2709 = new A(a2707.get());
        A a2710 = new A(a2708.get());

        A a2711 = new A(a2709.get());
        A a2712 = new A(a2710.get());

        A a2713 = new A(a2711.get());
        A a2714 = new A(a2712.get());

        A a2715 = new A(a2713.get());
        A a2716 = new A(a2714.get());

        A a2717 = new A(a2715.get());
        A a2718 = new A(a2716.get());

        A a2719 = new A(a2717.get());
        A a2720 = new A(a2718.get());

        A a2721 = new A(a2719.get());
        A a2722 = new A(a2720.get());

        A a2723 = new A(a2721.get());
        A a2724 = new A(a2722.get());

        A a2725 = new A(a2723.get());
        A a2726 = new A(a2724.get());

        A a2727 = new A(a2725.get());
        A a2728 = new A(a2726.get());

        A a2729 = new A(a2727.get());
        A a2730 = new A(a2728.get());

        A a2731 = new A(a2729.get());
        A a2732 = new A(a2730.get());

        A a2733 = new A(a2731.get());
        A a2734 = new A(a2732.get());

        A a2735 = new A(a2733.get());
        A a2736 = new A(a2734.get());

        A a2737 = new A(a2735.get());
        A a2738 = new A(a2736.get());

        A a2739 = new A(a2737.get());
        A a2740 = new A(a2738.get());

        A a2741 = new A(a2739.get());
        A a2742 = new A(a2740.get());

        A a2743 = new A(a2741.get());
        A a2744 = new A(a2742.get());

        A a2745 = new A(a2743.get());
        A a2746 = new A(a2744.get());

        A a2747 = new A(a2745.get());
        A a2748 = new A(a2746.get());

        A a2749 = new A(a2747.get());
        A a2750 = new A(a2748.get());

        A a2751 = new A(a2749.get());
        A a2752 = new A(a2750.get());

        A a2753 = new A(a2751.get());
        A a2754 = new A(a2752.get());

        A a2755 = new A(a2753.get());
        A a2756 = new A(a2754.get());

        A a2757 = new A(a2755.get());
        A a2758 = new A(a2756.get());

        A a2759 = new A(a2757.get());
        A a2760 = new A(a2758.get());

        A a2761 = new A(a2759.get());
        A a2762 = new A(a2760.get());

        A a2763 = new A(a2761.get());
        A a2764 = new A(a2762.get());

        A a2765 = new A(a2763.get());
        A a2766 = new A(a2764.get());

        A a2767 = new A(a2765.get());
        A a2768 = new A(a2766.get());

        A a2769 = new A(a2767.get());
        A a2770 = new A(a2768.get());

        A a2771 = new A(a2769.get());
        A a2772 = new A(a2770.get());

        A a2773 = new A(a2771.get());
        A a2774 = new A(a2772.get());

        A a2775 = new A(a2773.get());
        A a2776 = new A(a2774.get());

        A a2777 = new A(a2775.get());
        A a2778 = new A(a2776.get());

        A a2779 = new A(a2777.get());
        A a2780 = new A(a2778.get());

        A a2781 = new A(a2779.get());
        A a2782 = new A(a2780.get());

        A a2783 = new A(a2781.get());
        A a2784 = new A(a2782.get());

        A a2785 = new A(a2783.get());
        A a2786 = new A(a2784.get());

        A a2787 = new A(a2785.get());
        A a2788 = new A(a2786.get());

        A a2789 = new A(a2787.get());
        A a2790 = new A(a2788.get());

        A a2791 = new A(a2789.get());
        A a2792 = new A(a2790.get());

        A a2793 = new A(a2791.get());
        A a2794 = new A(a2792.get());

        A a2795 = new A(a2793.get());
        A a2796 = new A(a2794.get());

        A a2797 = new A(a2795.get());
        A a2798 = new A(a2796.get());

        A a2799 = new A(a2797.get());
        A a2800 = new A(a2798.get());

        A a2801 = new A(a2799.get());
        A a2802 = new A(a2800.get());

        A a2803 = new A(a2801.get());
        A a2804 = new A(a2802.get());

        A a2805 = new A(a2803.get());
        A a2806 = new A(a2804.get());

        A a2807 = new A(a2805.get());
        A a2808 = new A(a2806.get());

        A a2809 = new A(a2807.get());
        A a2810 = new A(a2808.get());

        A a2811 = new A(a2809.get());
        A a2812 = new A(a2810.get());

        A a2813 = new A(a2811.get());
        A a2814 = new A(a2812.get());

        A a2815 = new A(a2813.get());
        A a2816 = new A(a2814.get());

        A a2817 = new A(a2815.get());
        A a2818 = new A(a2816.get());

        A a2819 = new A(a2817.get());
        A a2820 = new A(a2818.get());

        A a2821 = new A(a2819.get());
        A a2822 = new A(a2820.get());

        A a2823 = new A(a2821.get());
        A a2824 = new A(a2822.get());

        A a2825 = new A(a2823.get());
        A a2826 = new A(a2824.get());

        A a2827 = new A(a2825.get());
        A a2828 = new A(a2826.get());

        A a2829 = new A(a2827.get());
        A a2830 = new A(a2828.get());

        A a2831 = new A(a2829.get());
        A a2832 = new A(a2830.get());

        A a2833 = new A(a2831.get());
        A a2834 = new A(a2832.get());

        A a2835 = new A(a2833.get());
        A a2836 = new A(a2834.get());

        A a2837 = new A(a2835.get());
        A a2838 = new A(a2836.get());

        A a2839 = new A(a2837.get());
        A a2840 = new A(a2838.get());

        A a2841 = new A(a2839.get());
        A a2842 = new A(a2840.get());

        A a2843 = new A(a2841.get());
        A a2844 = new A(a2842.get());

        A a2845 = new A(a2843.get());
        A a2846 = new A(a2844.get());

        A a2847 = new A(a2845.get());
        A a2848 = new A(a2846.get());

        A a2849 = new A(a2847.get());
        A a2850 = new A(a2848.get());

        A a2851 = new A(a2849.get());
        A a2852 = new A(a2850.get());

        A a2853 = new A(a2851.get());
        A a2854 = new A(a2852.get());

        A a2855 = new A(a2853.get());
        A a2856 = new A(a2854.get());

        A a2857 = new A(a2855.get());
        A a2858 = new A(a2856.get());

        A a2859 = new A(a2857.get());
        A a2860 = new A(a2858.get());

        A a2861 = new A(a2859.get());
        A a2862 = new A(a2860.get());

        A a2863 = new A(a2861.get());
        A a2864 = new A(a2862.get());

        A a2865 = new A(a2863.get());
        A a2866 = new A(a2864.get());

        A a2867 = new A(a2865.get());
        A a2868 = new A(a2866.get());

        A a2869 = new A(a2867.get());
        A a2870 = new A(a2868.get());

        A a2871 = new A(a2869.get());
        A a2872 = new A(a2870.get());

        A a2873 = new A(a2871.get());
        A a2874 = new A(a2872.get());

        A a2875 = new A(a2873.get());
        A a2876 = new A(a2874.get());

        A a2877 = new A(a2875.get());
        A a2878 = new A(a2876.get());

        A a2879 = new A(a2877.get());
        A a2880 = new A(a2878.get());

        A a2881 = new A(a2879.get());
        A a2882 = new A(a2880.get());

        A a2883 = new A(a2881.get());
        A a2884 = new A(a2882.get());

        A a2885 = new A(a2883.get());
        A a2886 = new A(a2884.get());

        A a2887 = new A(a2885.get());
        A a2888 = new A(a2886.get());

        A a2889 = new A(a2887.get());
        A a2890 = new A(a2888.get());

        A a2891 = new A(a2889.get());
        A a2892 = new A(a2890.get());

        A a2893 = new A(a2891.get());
        A a2894 = new A(a2892.get());

        A a2895 = new A(a2893.get());
        A a2896 = new A(a2894.get());

        A a2897 = new A(a2895.get());
        A a2898 = new A(a2896.get());

        A a2899 = new A(a2897.get());
        A a2900 = new A(a2898.get());

        A a2901 = new A(a2899.get());
        A a2902 = new A(a2900.get());

        A a2903 = new A(a2901.get());
        A a2904 = new A(a2902.get());

        A a2905 = new A(a2903.get());
        A a2906 = new A(a2904.get());

        A a2907 = new A(a2905.get());
        A a2908 = new A(a2906.get());

        A a2909 = new A(a2907.get());
        A a2910 = new A(a2908.get());

        A a2911 = new A(a2909.get());
        A a2912 = new A(a2910.get());

        A a2913 = new A(a2911.get());
        A a2914 = new A(a2912.get());

        A a2915 = new A(a2913.get());
        A a2916 = new A(a2914.get());

        A a2917 = new A(a2915.get());
        A a2918 = new A(a2916.get());

        A a2919 = new A(a2917.get());
        A a2920 = new A(a2918.get());

        A a2921 = new A(a2919.get());
        A a2922 = new A(a2920.get());

        A a2923 = new A(a2921.get());
        A a2924 = new A(a2922.get());

        A a2925 = new A(a2923.get());
        A a2926 = new A(a2924.get());

        A a2927 = new A(a2925.get());
        A a2928 = new A(a2926.get());

        A a2929 = new A(a2927.get());
        A a2930 = new A(a2928.get());

        A a2931 = new A(a2929.get());
        A a2932 = new A(a2930.get());

        A a2933 = new A(a2931.get());
        A a2934 = new A(a2932.get());

        A a2935 = new A(a2933.get());
        A a2936 = new A(a2934.get());

        A a2937 = new A(a2935.get());
        A a2938 = new A(a2936.get());

        A a2939 = new A(a2937.get());
        A a2940 = new A(a2938.get());

        A a2941 = new A(a2939.get());
        A a2942 = new A(a2940.get());

        A a2943 = new A(a2941.get());
        A a2944 = new A(a2942.get());

        A a2945 = new A(a2943.get());
        A a2946 = new A(a2944.get());

        A a2947 = new A(a2945.get());
        A a2948 = new A(a2946.get());

        A a2949 = new A(a2947.get());
        A a2950 = new A(a2948.get());

        A a2951 = new A(a2949.get());
        A a2952 = new A(a2950.get());

        A a2953 = new A(a2951.get());
        A a2954 = new A(a2952.get());

        A a2955 = new A(a2953.get());
        A a2956 = new A(a2954.get());

        A a2957 = new A(a2955.get());
        A a2958 = new A(a2956.get());

        A a2959 = new A(a2957.get());
        A a2960 = new A(a2958.get());

        A a2961 = new A(a2959.get());
        A a2962 = new A(a2960.get());

        A a2963 = new A(a2961.get());
        A a2964 = new A(a2962.get());

        A a2965 = new A(a2963.get());
        A a2966 = new A(a2964.get());

        A a2967 = new A(a2965.get());
        A a2968 = new A(a2966.get());

        A a2969 = new A(a2967.get());
        A a2970 = new A(a2968.get());

        A a2971 = new A(a2969.get());
        A a2972 = new A(a2970.get());

        A a2973 = new A(a2971.get());
        A a2974 = new A(a2972.get());

        A a2975 = new A(a2973.get());
        A a2976 = new A(a2974.get());

        A a2977 = new A(a2975.get());
        A a2978 = new A(a2976.get());

        A a2979 = new A(a2977.get());
        A a2980 = new A(a2978.get());

        A a2981 = new A(a2979.get());
        A a2982 = new A(a2980.get());

        A a2983 = new A(a2981.get());
        A a2984 = new A(a2982.get());

        A a2985 = new A(a2983.get());
        A a2986 = new A(a2984.get());

        A a2987 = new A(a2985.get());
        A a2988 = new A(a2986.get());

        A a2989 = new A(a2987.get());
        A a2990 = new A(a2988.get());

        A a2991 = new A(a2989.get());
        A a2992 = new A(a2990.get());

        A a2993 = new A(a2991.get());
        A a2994 = new A(a2992.get());

        A a2995 = new A(a2993.get());
        A a2996 = new A(a2994.get());

        A a2997 = new A(a2995.get());
        A a2998 = new A(a2996.get());

        A a2999 = new A(a2997.get());
        A a3000 = new A(a2998.get());

        return foo6(a2999, a3000);
      }
       
      public static boolean foo6(A a2999, A a3000) {
    	  
        A a3001 = new A(a2999.get());
        A a3002 = new A(a3000.get());

        A a3003 = new A(a3001.get());
        A a3004 = new A(a3002.get());

        A a3005 = new A(a3003.get());
        A a3006 = new A(a3004.get());

        A a3007 = new A(a3005.get());
        A a3008 = new A(a3006.get());

        A a3009 = new A(a3007.get());
        A a3010 = new A(a3008.get());

        A a3011 = new A(a3009.get());
        A a3012 = new A(a3010.get());

        A a3013 = new A(a3011.get());
        A a3014 = new A(a3012.get());

        A a3015 = new A(a3013.get());
        A a3016 = new A(a3014.get());

        A a3017 = new A(a3015.get());
        A a3018 = new A(a3016.get());

        A a3019 = new A(a3017.get());
        A a3020 = new A(a3018.get());

        A a3021 = new A(a3019.get());
        A a3022 = new A(a3020.get());

        A a3023 = new A(a3021.get());
        A a3024 = new A(a3022.get());

        A a3025 = new A(a3023.get());
        A a3026 = new A(a3024.get());

        A a3027 = new A(a3025.get());
        A a3028 = new A(a3026.get());

        A a3029 = new A(a3027.get());
        A a3030 = new A(a3028.get());

        A a3031 = new A(a3029.get());
        A a3032 = new A(a3030.get());

        A a3033 = new A(a3031.get());
        A a3034 = new A(a3032.get());

        A a3035 = new A(a3033.get());
        A a3036 = new A(a3034.get());

        A a3037 = new A(a3035.get());
        A a3038 = new A(a3036.get());

        A a3039 = new A(a3037.get());
        A a3040 = new A(a3038.get());

        A a3041 = new A(a3039.get());
        A a3042 = new A(a3040.get());

        A a3043 = new A(a3041.get());
        A a3044 = new A(a3042.get());

        A a3045 = new A(a3043.get());
        A a3046 = new A(a3044.get());

        A a3047 = new A(a3045.get());
        A a3048 = new A(a3046.get());

        A a3049 = new A(a3047.get());
        A a3050 = new A(a3048.get());

        A a3051 = new A(a3049.get());
        A a3052 = new A(a3050.get());

        A a3053 = new A(a3051.get());
        A a3054 = new A(a3052.get());

        A a3055 = new A(a3053.get());
        A a3056 = new A(a3054.get());

        A a3057 = new A(a3055.get());
        A a3058 = new A(a3056.get());

        A a3059 = new A(a3057.get());
        A a3060 = new A(a3058.get());

        A a3061 = new A(a3059.get());
        A a3062 = new A(a3060.get());

        A a3063 = new A(a3061.get());
        A a3064 = new A(a3062.get());

        A a3065 = new A(a3063.get());
        A a3066 = new A(a3064.get());

        A a3067 = new A(a3065.get());
        A a3068 = new A(a3066.get());

        A a3069 = new A(a3067.get());
        A a3070 = new A(a3068.get());

        A a3071 = new A(a3069.get());
        A a3072 = new A(a3070.get());

        A a3073 = new A(a3071.get());
        A a3074 = new A(a3072.get());

        A a3075 = new A(a3073.get());
        A a3076 = new A(a3074.get());

        A a3077 = new A(a3075.get());
        A a3078 = new A(a3076.get());

        A a3079 = new A(a3077.get());
        A a3080 = new A(a3078.get());

        A a3081 = new A(a3079.get());
        A a3082 = new A(a3080.get());

        A a3083 = new A(a3081.get());
        A a3084 = new A(a3082.get());

        A a3085 = new A(a3083.get());
        A a3086 = new A(a3084.get());

        A a3087 = new A(a3085.get());
        A a3088 = new A(a3086.get());

        A a3089 = new A(a3087.get());
        A a3090 = new A(a3088.get());

        A a3091 = new A(a3089.get());
        A a3092 = new A(a3090.get());

        A a3093 = new A(a3091.get());
        A a3094 = new A(a3092.get());

        A a3095 = new A(a3093.get());
        A a3096 = new A(a3094.get());

        A a3097 = new A(a3095.get());
        A a3098 = new A(a3096.get());

        A a3099 = new A(a3097.get());
        A a3100 = new A(a3098.get());

        A a3101 = new A(a3099.get());
        A a3102 = new A(a3100.get());

        A a3103 = new A(a3101.get());
        A a3104 = new A(a3102.get());

        A a3105 = new A(a3103.get());
        A a3106 = new A(a3104.get());

        A a3107 = new A(a3105.get());
        A a3108 = new A(a3106.get());

        A a3109 = new A(a3107.get());
        A a3110 = new A(a3108.get());

        A a3111 = new A(a3109.get());
        A a3112 = new A(a3110.get());

        A a3113 = new A(a3111.get());
        A a3114 = new A(a3112.get());

        A a3115 = new A(a3113.get());
        A a3116 = new A(a3114.get());

        A a3117 = new A(a3115.get());
        A a3118 = new A(a3116.get());

        A a3119 = new A(a3117.get());
        A a3120 = new A(a3118.get());

        A a3121 = new A(a3119.get());
        A a3122 = new A(a3120.get());

        A a3123 = new A(a3121.get());
        A a3124 = new A(a3122.get());

        A a3125 = new A(a3123.get());
        A a3126 = new A(a3124.get());

        A a3127 = new A(a3125.get());
        A a3128 = new A(a3126.get());

        A a3129 = new A(a3127.get());
        A a3130 = new A(a3128.get());

        A a3131 = new A(a3129.get());
        A a3132 = new A(a3130.get());

        A a3133 = new A(a3131.get());
        A a3134 = new A(a3132.get());

        A a3135 = new A(a3133.get());
        A a3136 = new A(a3134.get());

        A a3137 = new A(a3135.get());
        A a3138 = new A(a3136.get());

        A a3139 = new A(a3137.get());
        A a3140 = new A(a3138.get());

        A a3141 = new A(a3139.get());
        A a3142 = new A(a3140.get());

        A a3143 = new A(a3141.get());
        A a3144 = new A(a3142.get());

        A a3145 = new A(a3143.get());
        A a3146 = new A(a3144.get());

        A a3147 = new A(a3145.get());
        A a3148 = new A(a3146.get());

        A a3149 = new A(a3147.get());
        A a3150 = new A(a3148.get());

        A a3151 = new A(a3149.get());
        A a3152 = new A(a3150.get());

        A a3153 = new A(a3151.get());
        A a3154 = new A(a3152.get());

        A a3155 = new A(a3153.get());
        A a3156 = new A(a3154.get());

        A a3157 = new A(a3155.get());
        A a3158 = new A(a3156.get());

        A a3159 = new A(a3157.get());
        A a3160 = new A(a3158.get());

        A a3161 = new A(a3159.get());
        A a3162 = new A(a3160.get());

        A a3163 = new A(a3161.get());
        A a3164 = new A(a3162.get());

        A a3165 = new A(a3163.get());
        A a3166 = new A(a3164.get());

        A a3167 = new A(a3165.get());
        A a3168 = new A(a3166.get());

        A a3169 = new A(a3167.get());
        A a3170 = new A(a3168.get());

        A a3171 = new A(a3169.get());
        A a3172 = new A(a3170.get());

        A a3173 = new A(a3171.get());
        A a3174 = new A(a3172.get());

        A a3175 = new A(a3173.get());
        A a3176 = new A(a3174.get());

        A a3177 = new A(a3175.get());
        A a3178 = new A(a3176.get());

        A a3179 = new A(a3177.get());
        A a3180 = new A(a3178.get());

        A a3181 = new A(a3179.get());
        A a3182 = new A(a3180.get());

        A a3183 = new A(a3181.get());
        A a3184 = new A(a3182.get());

        A a3185 = new A(a3183.get());
        A a3186 = new A(a3184.get());

        A a3187 = new A(a3185.get());
        A a3188 = new A(a3186.get());

        A a3189 = new A(a3187.get());
        A a3190 = new A(a3188.get());

        A a3191 = new A(a3189.get());
        A a3192 = new A(a3190.get());

        A a3193 = new A(a3191.get());
        A a3194 = new A(a3192.get());

        A a3195 = new A(a3193.get());
        A a3196 = new A(a3194.get());

        A a3197 = new A(a3195.get());
        A a3198 = new A(a3196.get());

        A a3199 = new A(a3197.get());
        A a3200 = new A(a3198.get());

        A a3201 = new A(a3199.get());
        A a3202 = new A(a3200.get());

        A a3203 = new A(a3201.get());
        A a3204 = new A(a3202.get());

        A a3205 = new A(a3203.get());
        A a3206 = new A(a3204.get());

        A a3207 = new A(a3205.get());
        A a3208 = new A(a3206.get());

        A a3209 = new A(a3207.get());
        A a3210 = new A(a3208.get());

        A a3211 = new A(a3209.get());
        A a3212 = new A(a3210.get());

        A a3213 = new A(a3211.get());
        A a3214 = new A(a3212.get());

        A a3215 = new A(a3213.get());
        A a3216 = new A(a3214.get());

        A a3217 = new A(a3215.get());
        A a3218 = new A(a3216.get());

        A a3219 = new A(a3217.get());
        A a3220 = new A(a3218.get());

        A a3221 = new A(a3219.get());
        A a3222 = new A(a3220.get());

        A a3223 = new A(a3221.get());
        A a3224 = new A(a3222.get());

        A a3225 = new A(a3223.get());
        A a3226 = new A(a3224.get());

        A a3227 = new A(a3225.get());
        A a3228 = new A(a3226.get());

        A a3229 = new A(a3227.get());
        A a3230 = new A(a3228.get());

        A a3231 = new A(a3229.get());
        A a3232 = new A(a3230.get());

        A a3233 = new A(a3231.get());
        A a3234 = new A(a3232.get());

        A a3235 = new A(a3233.get());
        A a3236 = new A(a3234.get());

        A a3237 = new A(a3235.get());
        A a3238 = new A(a3236.get());

        A a3239 = new A(a3237.get());
        A a3240 = new A(a3238.get());

        A a3241 = new A(a3239.get());
        A a3242 = new A(a3240.get());

        A a3243 = new A(a3241.get());
        A a3244 = new A(a3242.get());

        A a3245 = new A(a3243.get());
        A a3246 = new A(a3244.get());

        A a3247 = new A(a3245.get());
        A a3248 = new A(a3246.get());

        A a3249 = new A(a3247.get());
        A a3250 = new A(a3248.get());

        A a3251 = new A(a3249.get());
        A a3252 = new A(a3250.get());

        A a3253 = new A(a3251.get());
        A a3254 = new A(a3252.get());

        A a3255 = new A(a3253.get());
        A a3256 = new A(a3254.get());

        A a3257 = new A(a3255.get());
        A a3258 = new A(a3256.get());

        A a3259 = new A(a3257.get());
        A a3260 = new A(a3258.get());

        A a3261 = new A(a3259.get());
        A a3262 = new A(a3260.get());

        A a3263 = new A(a3261.get());
        A a3264 = new A(a3262.get());

        A a3265 = new A(a3263.get());
        A a3266 = new A(a3264.get());

        A a3267 = new A(a3265.get());
        A a3268 = new A(a3266.get());

        A a3269 = new A(a3267.get());
        A a3270 = new A(a3268.get());

        A a3271 = new A(a3269.get());
        A a3272 = new A(a3270.get());

        A a3273 = new A(a3271.get());
        A a3274 = new A(a3272.get());

        A a3275 = new A(a3273.get());
        A a3276 = new A(a3274.get());

        A a3277 = new A(a3275.get());
        A a3278 = new A(a3276.get());

        A a3279 = new A(a3277.get());
        A a3280 = new A(a3278.get());

        A a3281 = new A(a3279.get());
        A a3282 = new A(a3280.get());

        A a3283 = new A(a3281.get());
        A a3284 = new A(a3282.get());

        A a3285 = new A(a3283.get());
        A a3286 = new A(a3284.get());

        A a3287 = new A(a3285.get());
        A a3288 = new A(a3286.get());

        A a3289 = new A(a3287.get());
        A a3290 = new A(a3288.get());

        A a3291 = new A(a3289.get());
        A a3292 = new A(a3290.get());

        A a3293 = new A(a3291.get());
        A a3294 = new A(a3292.get());

        A a3295 = new A(a3293.get());
        A a3296 = new A(a3294.get());

        A a3297 = new A(a3295.get());
        A a3298 = new A(a3296.get());

        A a3299 = new A(a3297.get());
        A a3300 = new A(a3298.get());

        A a3301 = new A(a3299.get());
        A a3302 = new A(a3300.get());

        A a3303 = new A(a3301.get());
        A a3304 = new A(a3302.get());

        A a3305 = new A(a3303.get());
        A a3306 = new A(a3304.get());

        A a3307 = new A(a3305.get());
        A a3308 = new A(a3306.get());

        A a3309 = new A(a3307.get());
        A a3310 = new A(a3308.get());

        A a3311 = new A(a3309.get());
        A a3312 = new A(a3310.get());

        A a3313 = new A(a3311.get());
        A a3314 = new A(a3312.get());

        A a3315 = new A(a3313.get());
        A a3316 = new A(a3314.get());

        A a3317 = new A(a3315.get());
        A a3318 = new A(a3316.get());

        A a3319 = new A(a3317.get());
        A a3320 = new A(a3318.get());

        A a3321 = new A(a3319.get());
        A a3322 = new A(a3320.get());

        A a3323 = new A(a3321.get());
        A a3324 = new A(a3322.get());

        A a3325 = new A(a3323.get());
        A a3326 = new A(a3324.get());

        A a3327 = new A(a3325.get());
        A a3328 = new A(a3326.get());

        A a3329 = new A(a3327.get());
        A a3330 = new A(a3328.get());

        A a3331 = new A(a3329.get());
        A a3332 = new A(a3330.get());

        A a3333 = new A(a3331.get());
        A a3334 = new A(a3332.get());

        A a3335 = new A(a3333.get());
        A a3336 = new A(a3334.get());

        A a3337 = new A(a3335.get());
        A a3338 = new A(a3336.get());

        A a3339 = new A(a3337.get());
        A a3340 = new A(a3338.get());

        A a3341 = new A(a3339.get());
        A a3342 = new A(a3340.get());

        A a3343 = new A(a3341.get());
        A a3344 = new A(a3342.get());

        A a3345 = new A(a3343.get());
        A a3346 = new A(a3344.get());

        A a3347 = new A(a3345.get());
        A a3348 = new A(a3346.get());

        A a3349 = new A(a3347.get());
        A a3350 = new A(a3348.get());

        A a3351 = new A(a3349.get());
        A a3352 = new A(a3350.get());

        A a3353 = new A(a3351.get());
        A a3354 = new A(a3352.get());

        A a3355 = new A(a3353.get());
        A a3356 = new A(a3354.get());

        A a3357 = new A(a3355.get());
        A a3358 = new A(a3356.get());

        A a3359 = new A(a3357.get());
        A a3360 = new A(a3358.get());

        A a3361 = new A(a3359.get());
        A a3362 = new A(a3360.get());

        A a3363 = new A(a3361.get());
        A a3364 = new A(a3362.get());

        A a3365 = new A(a3363.get());
        A a3366 = new A(a3364.get());

        A a3367 = new A(a3365.get());
        A a3368 = new A(a3366.get());

        A a3369 = new A(a3367.get());
        A a3370 = new A(a3368.get());

        A a3371 = new A(a3369.get());
        A a3372 = new A(a3370.get());

        A a3373 = new A(a3371.get());
        A a3374 = new A(a3372.get());

        A a3375 = new A(a3373.get());
        A a3376 = new A(a3374.get());

        A a3377 = new A(a3375.get());
        A a3378 = new A(a3376.get());

        A a3379 = new A(a3377.get());
        A a3380 = new A(a3378.get());

        A a3381 = new A(a3379.get());
        A a3382 = new A(a3380.get());

        A a3383 = new A(a3381.get());
        A a3384 = new A(a3382.get());

        A a3385 = new A(a3383.get());
        A a3386 = new A(a3384.get());

        A a3387 = new A(a3385.get());
        A a3388 = new A(a3386.get());

        A a3389 = new A(a3387.get());
        A a3390 = new A(a3388.get());

        A a3391 = new A(a3389.get());
        A a3392 = new A(a3390.get());

        A a3393 = new A(a3391.get());
        A a3394 = new A(a3392.get());

        A a3395 = new A(a3393.get());
        A a3396 = new A(a3394.get());

        A a3397 = new A(a3395.get());
        A a3398 = new A(a3396.get());

        A a3399 = new A(a3397.get());
        A a3400 = new A(a3398.get());

        A a3401 = new A(a3399.get());
        A a3402 = new A(a3400.get());

        A a3403 = new A(a3401.get());
        A a3404 = new A(a3402.get());

        A a3405 = new A(a3403.get());
        A a3406 = new A(a3404.get());

        A a3407 = new A(a3405.get());
        A a3408 = new A(a3406.get());

        A a3409 = new A(a3407.get());
        A a3410 = new A(a3408.get());

        A a3411 = new A(a3409.get());
        A a3412 = new A(a3410.get());

        A a3413 = new A(a3411.get());
        A a3414 = new A(a3412.get());

        A a3415 = new A(a3413.get());
        A a3416 = new A(a3414.get());

        A a3417 = new A(a3415.get());
        A a3418 = new A(a3416.get());

        A a3419 = new A(a3417.get());
        A a3420 = new A(a3418.get());

        A a3421 = new A(a3419.get());
        A a3422 = new A(a3420.get());

        A a3423 = new A(a3421.get());
        A a3424 = new A(a3422.get());

        A a3425 = new A(a3423.get());
        A a3426 = new A(a3424.get());

        A a3427 = new A(a3425.get());
        A a3428 = new A(a3426.get());

        A a3429 = new A(a3427.get());
        A a3430 = new A(a3428.get());

        A a3431 = new A(a3429.get());
        A a3432 = new A(a3430.get());

        A a3433 = new A(a3431.get());
        A a3434 = new A(a3432.get());

        A a3435 = new A(a3433.get());
        A a3436 = new A(a3434.get());

        A a3437 = new A(a3435.get());
        A a3438 = new A(a3436.get());

        A a3439 = new A(a3437.get());
        A a3440 = new A(a3438.get());

        A a3441 = new A(a3439.get());
        A a3442 = new A(a3440.get());

        A a3443 = new A(a3441.get());
        A a3444 = new A(a3442.get());

        A a3445 = new A(a3443.get());
        A a3446 = new A(a3444.get());

        A a3447 = new A(a3445.get());
        A a3448 = new A(a3446.get());

        A a3449 = new A(a3447.get());
        A a3450 = new A(a3448.get());

        A a3451 = new A(a3449.get());
        A a3452 = new A(a3450.get());

        A a3453 = new A(a3451.get());
        A a3454 = new A(a3452.get());

        A a3455 = new A(a3453.get());
        A a3456 = new A(a3454.get());

        A a3457 = new A(a3455.get());
        A a3458 = new A(a3456.get());

        A a3459 = new A(a3457.get());
        A a3460 = new A(a3458.get());

        A a3461 = new A(a3459.get());
        A a3462 = new A(a3460.get());

        A a3463 = new A(a3461.get());
        A a3464 = new A(a3462.get());

        A a3465 = new A(a3463.get());
        A a3466 = new A(a3464.get());

        A a3467 = new A(a3465.get());
        A a3468 = new A(a3466.get());

        A a3469 = new A(a3467.get());
        A a3470 = new A(a3468.get());

        A a3471 = new A(a3469.get());
        A a3472 = new A(a3470.get());

        A a3473 = new A(a3471.get());
        A a3474 = new A(a3472.get());

        A a3475 = new A(a3473.get());
        A a3476 = new A(a3474.get());

        A a3477 = new A(a3475.get());
        A a3478 = new A(a3476.get());

        A a3479 = new A(a3477.get());
        A a3480 = new A(a3478.get());

        A a3481 = new A(a3479.get());
        A a3482 = new A(a3480.get());

        A a3483 = new A(a3481.get());
        A a3484 = new A(a3482.get());

        A a3485 = new A(a3483.get());
        A a3486 = new A(a3484.get());

        A a3487 = new A(a3485.get());
        A a3488 = new A(a3486.get());

        A a3489 = new A(a3487.get());
        A a3490 = new A(a3488.get());

        A a3491 = new A(a3489.get());
        A a3492 = new A(a3490.get());

        A a3493 = new A(a3491.get());
        A a3494 = new A(a3492.get());

        A a3495 = new A(a3493.get());
        A a3496 = new A(a3494.get());

        A a3497 = new A(a3495.get());
        A a3498 = new A(a3496.get());

        A a3499 = new A(a3497.get());
        A a3500 = new A(a3498.get());

        return foo7(a3499, a3500);
      }
       
      public static boolean foo7(A a3499, A a3500) {
    	  
        A a3501 = new A(a3499.get());
        A a3502 = new A(a3500.get());

        A a3503 = new A(a3501.get());
        A a3504 = new A(a3502.get());

        A a3505 = new A(a3503.get());
        A a3506 = new A(a3504.get());

        A a3507 = new A(a3505.get());
        A a3508 = new A(a3506.get());

        A a3509 = new A(a3507.get());
        A a3510 = new A(a3508.get());

        A a3511 = new A(a3509.get());
        A a3512 = new A(a3510.get());

        A a3513 = new A(a3511.get());
        A a3514 = new A(a3512.get());

        A a3515 = new A(a3513.get());
        A a3516 = new A(a3514.get());

        A a3517 = new A(a3515.get());
        A a3518 = new A(a3516.get());

        A a3519 = new A(a3517.get());
        A a3520 = new A(a3518.get());

        A a3521 = new A(a3519.get());
        A a3522 = new A(a3520.get());

        A a3523 = new A(a3521.get());
        A a3524 = new A(a3522.get());

        A a3525 = new A(a3523.get());
        A a3526 = new A(a3524.get());

        A a3527 = new A(a3525.get());
        A a3528 = new A(a3526.get());

        A a3529 = new A(a3527.get());
        A a3530 = new A(a3528.get());

        A a3531 = new A(a3529.get());
        A a3532 = new A(a3530.get());

        A a3533 = new A(a3531.get());
        A a3534 = new A(a3532.get());

        A a3535 = new A(a3533.get());
        A a3536 = new A(a3534.get());

        A a3537 = new A(a3535.get());
        A a3538 = new A(a3536.get());

        A a3539 = new A(a3537.get());
        A a3540 = new A(a3538.get());

        A a3541 = new A(a3539.get());
        A a3542 = new A(a3540.get());

        A a3543 = new A(a3541.get());
        A a3544 = new A(a3542.get());

        A a3545 = new A(a3543.get());
        A a3546 = new A(a3544.get());

        A a3547 = new A(a3545.get());
        A a3548 = new A(a3546.get());

        A a3549 = new A(a3547.get());
        A a3550 = new A(a3548.get());

        A a3551 = new A(a3549.get());
        A a3552 = new A(a3550.get());

        A a3553 = new A(a3551.get());
        A a3554 = new A(a3552.get());

        A a3555 = new A(a3553.get());
        A a3556 = new A(a3554.get());

        A a3557 = new A(a3555.get());
        A a3558 = new A(a3556.get());

        A a3559 = new A(a3557.get());
        A a3560 = new A(a3558.get());

        A a3561 = new A(a3559.get());
        A a3562 = new A(a3560.get());

        A a3563 = new A(a3561.get());
        A a3564 = new A(a3562.get());

        A a3565 = new A(a3563.get());
        A a3566 = new A(a3564.get());

        A a3567 = new A(a3565.get());
        A a3568 = new A(a3566.get());

        A a3569 = new A(a3567.get());
        A a3570 = new A(a3568.get());

        A a3571 = new A(a3569.get());
        A a3572 = new A(a3570.get());

        A a3573 = new A(a3571.get());
        A a3574 = new A(a3572.get());

        A a3575 = new A(a3573.get());
        A a3576 = new A(a3574.get());

        A a3577 = new A(a3575.get());
        A a3578 = new A(a3576.get());

        A a3579 = new A(a3577.get());
        A a3580 = new A(a3578.get());

        A a3581 = new A(a3579.get());
        A a3582 = new A(a3580.get());

        A a3583 = new A(a3581.get());
        A a3584 = new A(a3582.get());

        A a3585 = new A(a3583.get());
        A a3586 = new A(a3584.get());

        A a3587 = new A(a3585.get());
        A a3588 = new A(a3586.get());

        A a3589 = new A(a3587.get());
        A a3590 = new A(a3588.get());

        A a3591 = new A(a3589.get());
        A a3592 = new A(a3590.get());

        A a3593 = new A(a3591.get());
        A a3594 = new A(a3592.get());

        A a3595 = new A(a3593.get());
        A a3596 = new A(a3594.get());

        A a3597 = new A(a3595.get());
        A a3598 = new A(a3596.get());

        A a3599 = new A(a3597.get());
        A a3600 = new A(a3598.get());

        A a3601 = new A(a3599.get());
        A a3602 = new A(a3600.get());

        A a3603 = new A(a3601.get());
        A a3604 = new A(a3602.get());

        A a3605 = new A(a3603.get());
        A a3606 = new A(a3604.get());

        A a3607 = new A(a3605.get());
        A a3608 = new A(a3606.get());

        A a3609 = new A(a3607.get());
        A a3610 = new A(a3608.get());

        A a3611 = new A(a3609.get());
        A a3612 = new A(a3610.get());

        A a3613 = new A(a3611.get());
        A a3614 = new A(a3612.get());

        A a3615 = new A(a3613.get());
        A a3616 = new A(a3614.get());

        A a3617 = new A(a3615.get());
        A a3618 = new A(a3616.get());

        A a3619 = new A(a3617.get());
        A a3620 = new A(a3618.get());

        A a3621 = new A(a3619.get());
        A a3622 = new A(a3620.get());

        A a3623 = new A(a3621.get());
        A a3624 = new A(a3622.get());

        A a3625 = new A(a3623.get());
        A a3626 = new A(a3624.get());

        A a3627 = new A(a3625.get());
        A a3628 = new A(a3626.get());

        A a3629 = new A(a3627.get());
        A a3630 = new A(a3628.get());

        A a3631 = new A(a3629.get());
        A a3632 = new A(a3630.get());

        A a3633 = new A(a3631.get());
        A a3634 = new A(a3632.get());

        A a3635 = new A(a3633.get());
        A a3636 = new A(a3634.get());

        A a3637 = new A(a3635.get());
        A a3638 = new A(a3636.get());

        A a3639 = new A(a3637.get());
        A a3640 = new A(a3638.get());

        A a3641 = new A(a3639.get());
        A a3642 = new A(a3640.get());

        A a3643 = new A(a3641.get());
        A a3644 = new A(a3642.get());

        A a3645 = new A(a3643.get());
        A a3646 = new A(a3644.get());

        A a3647 = new A(a3645.get());
        A a3648 = new A(a3646.get());

        A a3649 = new A(a3647.get());
        A a3650 = new A(a3648.get());

        A a3651 = new A(a3649.get());
        A a3652 = new A(a3650.get());

        A a3653 = new A(a3651.get());
        A a3654 = new A(a3652.get());

        A a3655 = new A(a3653.get());
        A a3656 = new A(a3654.get());

        A a3657 = new A(a3655.get());
        A a3658 = new A(a3656.get());

        A a3659 = new A(a3657.get());
        A a3660 = new A(a3658.get());

        A a3661 = new A(a3659.get());
        A a3662 = new A(a3660.get());

        A a3663 = new A(a3661.get());
        A a3664 = new A(a3662.get());

        A a3665 = new A(a3663.get());
        A a3666 = new A(a3664.get());

        A a3667 = new A(a3665.get());
        A a3668 = new A(a3666.get());

        A a3669 = new A(a3667.get());
        A a3670 = new A(a3668.get());

        A a3671 = new A(a3669.get());
        A a3672 = new A(a3670.get());

        A a3673 = new A(a3671.get());
        A a3674 = new A(a3672.get());

        A a3675 = new A(a3673.get());
        A a3676 = new A(a3674.get());

        A a3677 = new A(a3675.get());
        A a3678 = new A(a3676.get());

        A a3679 = new A(a3677.get());
        A a3680 = new A(a3678.get());

        A a3681 = new A(a3679.get());
        A a3682 = new A(a3680.get());

        A a3683 = new A(a3681.get());
        A a3684 = new A(a3682.get());

        A a3685 = new A(a3683.get());
        A a3686 = new A(a3684.get());

        A a3687 = new A(a3685.get());
        A a3688 = new A(a3686.get());

        A a3689 = new A(a3687.get());
        A a3690 = new A(a3688.get());

        A a3691 = new A(a3689.get());
        A a3692 = new A(a3690.get());

        A a3693 = new A(a3691.get());
        A a3694 = new A(a3692.get());

        A a3695 = new A(a3693.get());
        A a3696 = new A(a3694.get());


        return CoInFlowUserAPI.unlabel(a3696.get());
      }
  

      public static void main (String [] args) {
        foo(randBool());
      }

      /** Helper method to obtain a random boolean */
      static boolean randBool() {
          return System.currentTimeMillis() % 2 == 0;
      }

      /** Helper methot to obtain a random integer */
      static int randInt() {
        return (int) System.currentTimeMillis();
      }

  }

