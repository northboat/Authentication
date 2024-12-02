package cia.northboat.auth.sim;

import cia.northboat.auth.key.SchnorrKey;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.util.ArrayList;
import java.util.List;


// 完整的展示认证过程
// 密钥分发
// 挑战-响应
// 认证

public class Schnorr {
    private static Pairing bp = PairingFactory.getPairing("a.properties");

    private static Field G, Zr;

    private static List<SchnorrKey> list;

    // 初始化
    static{
        G = bp.getG1();
        Zr = bp.getZr();
        list = new ArrayList<>();
    }

    // 密钥生成
    public static SchnorrKey keyGen(){
        Element P = G.newRandomElement().getImmutable();
        Element x = Zr.newRandomElement().getImmutable();
        Element Z = P.mulZn(x.negate()).getImmutable();

        SchnorrKey k = new SchnorrKey(Z, P);
        list.add(k);

        return new SchnorrKey(x, P);
    }

    public static Element submit(Element P, Element r){
        return P.mulZn(r).getImmutable();
    }

    public static Element challenge(){
        return Zr.newRandomElement().getImmutable();
    }

    public static Element response(Element x, Element e, Element r){
        return x.mul(e).add(r).getImmutable();
    }

    public static boolean auth(Element X, Element y, Element e){
        for(SchnorrKey key: list){
            if(key.getP().mulZn(y).add(key.getZ().mulZn(e)).isEqual(X)){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        SchnorrKey key = keyGen();

        Element r = Zr.newRandomElement().getImmutable();
        Element X = submit(key.getP(), r);

        Element e = challenge();
        Element y = response(key.getZ(), e, r);

        boolean flag = auth(X, y, e);
        System.out.println(flag);
    }
}
