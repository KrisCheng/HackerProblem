//
//  main.cpp
//  disjunctiveNF
//
//  Created by Kris on 09/10/2017.
//  Copyright © 2017 Kris. All rights reserved.
//
//代码参考自  http://blog.csdn.net/icefire_tyh/article/details/52065626


#include <vector>
#include <stack>
using namespace std;

//按泛化程度排序，保证排在后面的假设不会不会包含前面的任何一个假设
static const char list[] = {
    0,0,0,
    0,0,1,0,0,2,0,0,3,0,1,0,0,2,0,0,3,0,1,0,0,2,0,0,
    0,1,1,0,1,2,0,1,3,0,2,1,0,2,2,0,2,3,0,3,1,0,3,2,0,3,3,
    1,0,1,1,0,2,1,0,3,2,0,1,2,0,2,2,0,3,
    1,1,0,1,2,0,1,3,0,2,1,0,2,2,0,2,3,0,
    1,1,1,1,1,2,1,1,3,1,2,1,1,2,2,1,2,3,1,3,1,1,3,2,1,3,3,
    2,1,1,2,1,2,2,1,3,2,2,1,2,2,2,2,2,3,2,3,1,2,3,2,2,3,3
};

//用来派生的抽象类
class hypos {
public:
    virtual int insert(int cur) = 0;
};

//单个的假设类
/*
 hypo_const  假设对应的具体假设集合
 */
class hypo :public hypos {
public:
    hypo(int a, int b, int c) {
        hypo_const = 0;
        vector<char>  p[3];
        if (a == 0) {
            p[0].push_back(1);
            p[0].push_back(2);
        }
        else
            p[0].push_back(a);
        if (b == 0) {
            p[1].push_back(1);
            p[1].push_back(2);
            p[1].push_back(3);
        }
        else
            p[1].push_back(b);
        if (c == 0) {
            p[2].push_back(1);
            p[2].push_back(2);
            p[2].push_back(3);
        }
        else
            p[2].push_back(c);
        for (unsigned int i = 0;i < p[0].size();i++)
            for (unsigned int j = 0;j < p[1].size();j++)
                for (unsigned int k = 0;k < p[2].size();k++)
                    hypo_const |= (1 << (p[0][i] * 9 + p[1][j] * 3 + p[2][k] - 13));
    }
    
    //判断是否要加入到析合式 如果还有具体假设没被包含，则加入
    int insert(int cur) {
        return (hypo_const & cur);
    };
    
private:
    int hypo_const;
};

//用于压入栈的派生类 用来实现非递归
/*
 hypo_tmp    记录这个假设入栈时，带入了哪些具体假设，出栈时要还原
 ptr         记录入栈时的位置
 */
class hypo_ss :public hypos {
public:
    hypo_ss(int _ptr,int tmp){
        hypo_tmp = tmp;
        ptr = _ptr;
    }
    int insert(int cur) {
        return 0;
    };
    int hypo_tmp;
    int ptr;
};

//用来循环遍历的类
/*
 sum     各个长度的析合式各有多少种可能
 ss      用来实现非递归的栈
 hypos_cur   当前没被包含的具体假设 初始值为0X3FFFF
 hyposs  48个假设集合
 */
class Traversal :public hypos {
public:
    Traversal() {
        hypos_cur = 0x3ffff;
        for(int i=0;i<48;i++)
            hyposs.push_back(hypo(list[3*i], list[3*i+1], list[3*i+2]));
    }
    
    //循环顺序遍历的主体
    //cur  初试的位置 设为0
    int insert(int cur) {
        //当前指向的位置
        int ptr = cur;
        while (1) {
            //退出条件 当最后一个假设作为第一个入栈的元素 表示遍历完成
            if (ptr > 47 && !ss.size()) break;
            //回退条件  扫描到最后或者所有具体假设都被包含
            if (hypos_cur == 0 || ptr>47) {
                hypo_ss hypo_tmp = ss.top();
                hypos_cur ^= hypo_tmp.hypo_tmp;
                ptr = hypo_tmp.ptr + 1;
                ss.pop();
                continue;
            }
            
            //入栈条件  如果该假设还有未被包含的具体假设 则入栈，并当前栈大小的计数加1
            if (int tmp =hyposs[ptr].insert(hypos_cur)) {
                hypos_cur ^= tmp;
                ss.push(hypo_ss(ptr, tmp));
                if (sum.size() < ss.size())
                    sum.push_back(0);
                sum[ss.size() - 1]++;
            }
            ptr++;
        }
        return 1;
    };
    //输出各个长度的可能数
    void print() {
        for (unsigned int i = 0;i < sum.size();i++)
            printf("length %d : %d\n", i + 1, sum[i]);
    }
private:
    vector<int> sum;
    stack<hypo_ss> ss;
    int hypos_cur;
    vector<hypo> hyposs;
};

int main()
{
    Traversal traversal;
    traversal.insert(0);
    traversal.print();
    return 0;
}
