using System;
using System.Security.Cryptography;
using System.Text;

namespace hello_csharp
{
    public class RsaDecryptExample
    {
        private readonly string PUBLIC_KEY = "<RSAKeyValue><Modulus>wmI6UDOjfjs/aDdn7hm2CvFs/Wc1E4C4GAzy20/ggLKZT+rf1EBe7034osMaFHne/FVQ56UNbphIYZtll6aaB+Z/r+Sm0oyOmr7S0wMftwBNaCnJN7rR4AlXIEJwfv9LZGhmt0VFvXdYm/0uRpmUaX+oU8mCCD/yxZp1w/m1x4TeUFMSpBMtWq+KcodbqBmNu7nLxWrbDkygxK1970dPADurT5MSCxdFR2sdkDNRLSYtAmXq6rMIsyhL8U+N0510zizVTlzcgB5p6vfDmUYocKy5l3LAYrk2JwPZ+c0Ik1jYcL8fU8sCSCwoXgY0fnXmfTDCoJ+xwd1nz7foiPLDww==</Modulus><Exponent>AQAB</Exponent></RSAKeyValue>";
        private readonly string PRIVATE_KEY = "<RSAKeyValue><Modulus>wmI6UDOjfjs/aDdn7hm2CvFs/Wc1E4C4GAzy20/ggLKZT+rf1EBe7034osMaFHne/FVQ56UNbphIYZtll6aaB+Z/r+Sm0oyOmr7S0wMftwBNaCnJN7rR4AlXIEJwfv9LZGhmt0VFvXdYm/0uRpmUaX+oU8mCCD/yxZp1w/m1x4TeUFMSpBMtWq+KcodbqBmNu7nLxWrbDkygxK1970dPADurT5MSCxdFR2sdkDNRLSYtAmXq6rMIsyhL8U+N0510zizVTlzcgB5p6vfDmUYocKy5l3LAYrk2JwPZ+c0Ik1jYcL8fU8sCSCwoXgY0fnXmfTDCoJ+xwd1nz7foiPLDww==</Modulus><Exponent>AQAB</Exponent><P>3xsQecso3ATToNmMe43qTSADzUt6WBokW8s7qtH4M2tT8dkt7N+VUKNh8MT+SWqCevG+mM6pX+fgwFfCpEXUYFEOBOpdY19iDByrckqnnfkrGuMlDLJ4unNEdwlqunt49MTh0aI527PnsNESaUzPY/OdBx2CNe1KHXgqOxKkXvc=</P><Q>3wsTRnFhKlolEh3Atwfbh8F18ZMF/hP4OrQZYIUHErZsirsYXA/APPIO1Rm3YCWPHReyMs5eKRUS2gYUqI/dLZx8H1CPNTFiTDrX99+B68C3TSvxWnJ2poW8jZuiz5ZhqP9+SmM+F1RoLyjhylXZlWBhC4szQpOJK9DVM8QO8pU=</Q><DP>hwRM8zgEcSomnIgrYTZgHe6K/nl3/8tKR5b1dAn/p7aDJbBl/5zLMvK9OcUtkmZ3skmMK9OdYuHj/OLfcySjQuqqjkT64UF/B49uHguZ5ps8/9eIu2dicouBWnyDb3l7mL1xX7lKRHBafYH5H2qRc46hcAXmoBYLLUxkWj2M5b8=</DP><DQ>m0QkeOc4krkKEWBw5Xks04ZTSoXkqAjSxlrVR6GTWO1xBepUfNHiKwr+VE6AnwfGZqQ2QrPyTHsFEo0lGGp9t+C1bz8vUgI8wYA9E6qd0LXpZGlwrFv+SNHUfmeXoRa4iWLCUZpggPEbTakR9T5qJ1hediDC4FfAJ6ZKpKyZEzk=</DQ><InverseQ>TrIoevy0kTo3DX+846k207yY9MBfnOI4tK9oNHW0/M4VhO4SHsbZblakMVTdQhCfhXuEtkHa8zOrpQLkAML6RJsOsDbANLU9oheTf8efdY/WsewoyzXoMiVanwiGi8DLQ5LW2rxv+BlC9FfEtgO/aTGLUwrVtXdKjPM/jp64u0k=</InverseQ><D>uSmQmq1V4/zUHZYJrklXm3z2cTxK1tUah+5tuHOSS4XVfMcjh6/5vsLrQT/QLAkbXAp5Eu+qp9rvt+qrD5Zny3bJ0udf8dhqTwXPFao4H75t/fcv8aJIIb1D/TrMpmzRiEfa8GFQWXbCNXUrC51oPkaYNZgSAYHI5GqKG7BWdEJ39Tc2q+F9/PDiukNgtFCEGYJByAVFpbidUAk8v8p+iE+W6Fvwhjw1m5f4yN4FOCBO+AenVfkzooD3Vp6Kala4iuru+rFKRUnqzCdcufbtSPnYSKiPachFBZs1U5FGFFwF0ASBxa5hLFfNrWVo4/eFQmVCMkovV8En6vT3F9BQIQ==</D></RSAKeyValue>";
        private readonly string ENCRYPTED_TEXT = "fM9134vsLcMYntcmz7uR3fB+1QcKAu+ohrAmKZsAanvckq+D0cJP/qLdF2QhNbbex9O7XDDCRsdvAuMnamDPbvi/1S7X4vH6Cu5Wduq3AerjmuQlLuX5XzemqpO3b92tfCgUape1l2T35aDHYHB0JmoFQFvLFCBzEIl4/oA50zXLGa8HPI8rc67KkrvC3voygFhDQ9ArZuS/CWdeHeteCejxY92mZSngyH9kheipd3TydNJE9Mp5iuTEdPKysEJTs7XpxBeo8zZ5xhe0VHnIgC9LCCaHfrQVPomNi0dGk+rWVQLt0MyU5hWBbQnWZaCujaZkGun1ZfrWzCbjrUiLPQ==";

        public void Run()
        {
            using (RSACryptoServiceProvider rsa = new RSACryptoServiceProvider())
            {
                //rsa.FromXmlString(PUBLIC_KEY);
                //byte[] encryptedBytesF = rsa.Encrypt(Encoding.UTF8.GetBytes("Hello"), false);
                //Console.WriteLine(Convert.ToBase64String(encryptedBytesF));
                rsa.FromXmlString(PRIVATE_KEY);
                byte[] encryptedBytes = Convert.FromBase64String(ENCRYPTED_TEXT);
                byte[] clearTextBytes = rsa.Decrypt(encryptedBytes, false);
                Console.WriteLine(Encoding.UTF8.GetString(clearTextBytes));
            }
        }
    }
}
