package cia.arkrypto.auth.crypto.impl;

import cia.arkrypto.auth.crypto.CipherSystem;
import cia.arkrypto.auth.dto.KeyPair;
import cia.arkrypto.auth.dto.CryptoMap;
import cia.arkrypto.auth.utils.FixedSizeQueue;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


// 完整的展示认证过程
// 密钥分发
// 签名
// 验证
public class SchnorrRFID extends CipherSystem {

    private static List<PK> list = null;

    public SchnorrRFID(Pairing BP, Field G1, Field G2, Field GT, Field Zr, Boolean sanitizable, Boolean updatable, int length) {
        super(BP, G1, G2, GT, Zr, sanitizable, updatable);
        list = new FixedSizeQueue<>(length);
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class PK{
        Element Z;
        Element P;
    }


    // 密钥生成
    @Override
    public KeyPair keygen(){
        Element P = randomG1();
        Element x = randomZ();
        Element Z = P.mulZn(x.negate()).getImmutable();
        Element r = randomZ();
        Element X = P.mulZn(r).getImmutable();


        list.add(new PK(Z, P));

        KeyPair k = new KeyPair();
        k.sk.put("x", x);
        k.sk.put("r", r);
        k.pk.put("X", X);

        return k;
    }

    @Override
    public CryptoMap sign(String message, CryptoMap sk){
        Element e = randomZ();
        Element x = sk.getElement("x", getZr());
        Element sigma = x.mul(e).add(sk.getElement("r", getZr())).getImmutable();

        CryptoMap signature = new CryptoMap();
        signature.put("e", e);
        signature.put("sigma", sigma);

        return signature;
    }




    @Override
    public Boolean verify(String message, CryptoMap pk, CryptoMap signature){
        Element sigma = signature.getElement("sigma", getZr());
        Element e = signature.getElement("e", getZr());
        Element X = pk.getElement("X", getG1());
        // 查表检查密钥
        for(PK key: list){
            if(key.getP().mulZn(sigma).add(key.getZ().mulZn(e)).isEqual(X)){
                return true;
            }
        }
        return false;
    }


}
