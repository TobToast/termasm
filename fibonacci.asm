; ===============================================
;                   Fibonacci
;                       By Tobias Pyhrr
; To test the virtual TERM64 CPU implementation
; ===============================================

    mov 1 -> r0
    mov 0 -> r1
    mov 0 -> r2
    mov 0 -> r3
    mov 50 -> r4
    
loop:

    cmp r3 r4
    cond 1 0x3820
    mov end -> pc {1}

    st r3 r2
    inc r3 -> r3

    add r0 r1 -> r2

    mov r1 -> r0
    mov r2 -> r1

    mov loop -> pc

end:

    hlt