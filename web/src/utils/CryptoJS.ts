import CryptoJS from "crypto-js";

const key = CryptoJS.enc.Utf8.parse("RDC_AES_CBC_KEY_");
const iv = CryptoJS.enc.Utf8.parse("RDC_AES_CBC_IV__");

// CBC模式
export default {
  // 加密
  encrypt(codeStr: string) {
    const code = CryptoJS.enc.Utf8.parse(codeStr);
    const encrypted = CryptoJS.AES.encrypt(code, key, {
      iv,
      module: CryptoJS.mode.CBC,
      padding: CryptoJS.pad.ZeroPadding
    });
    return encrypted.toString();
  },

  // 解密，暂时用不到
  decrypt(code: string) {
    const decrypt = CryptoJS.AES.decrypt(code, key, {
      iv,
      module: CryptoJS.mode.CBC,
      padding: CryptoJS.pad.ZeroPadding
    });
    return decrypt.toString(CryptoJS.enc.Utf8);
  }
};
