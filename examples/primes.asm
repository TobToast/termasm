; ===============================================
;           Sieve of Eratosthenes
;                       By Tobias Pyhrr
; To test the virtual TERM64 CPU Implementation 
; ===============================================

    mov 5120 -> r0
    mov 2 -> r1

outer_loop: 

    cmp r1 r0
    cond 1 0x3820
    mov end_sieve -> pc {1}
    
    mov #r1 -> r3 
    cond 1 0x0404 
    mov mark_multiples -> pc {1}
    
    inc r1 -> r1 
    
    mov outer_loop -> pc 

mark_multiples: 

    mul r1 r1 -> r5 

inner_loop: 

    cmp r5 r0
    cond 1 0x3820
    mov next_i -> pc {1}

    st r5 1

    add r1 r5 -> r5

    mov inner_loop -> pc 

next_i: 

    inc r1 -> r1 

    mov outer_loop -> pc 

end_sieve: 

    mov 2 -> r1 
    mov 0 -> r12 

extract_loop: 

    cmp r1 r0
    cond 1 0x3820 
    mov end -> pc {1}
    
    mov #r1 -> r3
    st r1 0
    cond 1 0x0404 
    st r12 r1 {1} 
    inc r12 -> r12 {1} 

    inc r1 -> r1 
    mov extract_loop -> pc 
    
end:

    hlt