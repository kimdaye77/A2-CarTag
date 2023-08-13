import { HTMLAttributes, MouseEventHandler, useContext } from 'react';
import { styled, useTheme } from 'styled-components';
import { ArrowLeft, ArrowRight, CloseIcon } from '../common/icons/Icons';
import {
  BodyKrMedium3,
  BodyKrMedium5,
  BodyKrRegular4,
  BodyKrRegular5,
  HeadingKrBold6,
  HeadingKrMedium6,
} from '../../styles/typefaces';
import { flexCenterCss } from '../../utils/commonStyle';
import ExtraOptionCard from '../cards/ExtraOptionCard';
import HmgTag from '../common/hmgTag/HmgTag';
import RectButton from '../common/buttons/RectButton';
import { DimmedBackground } from './DimmedBackground';
import { SimilarQuoteModalContext } from '../../context/SimilarQuoteModalContext';
import SimilarPriceBar from '../priceStaticBar/SimilarPriceBar';

interface ISimilarQuoteModal extends HTMLAttributes<HTMLDivElement> {}

export default function SimilarQuoteModal({ ...props }: ISimilarQuoteModal) {
  const theme = useTheme();
  const { visible, setVisible } = useContext(SimilarQuoteModalContext);
  const stopEvent: MouseEventHandler<HTMLDivElement> = (e) => {
    e.stopPropagation();
  };
  return (
    <DimmedBackground $displayDimmed={visible} {...props}>
      <Modal onClick={stopEvent}>
        <Header>
          <CloseBtn onClick={() => setVisible(false)}>
            <CloseIcon />
          </CloseBtn>
        </Header>
        <InfoWrapper>
          <TextWrapper>
            <TitleText>
              <BlueText>내 견적과 비슷한 실제 출고 견적</BlueText>들을 확인하고 비교해보세요.
            </TitleText>
            <DescText>
              *유사 출고 견적이란,
              <br />내 견적과 해시태그 유사도가 높은 다른 사람들의 실제 출고 견적이에요.
            </DescText>
          </TextWrapper>
          <SimilarPriceBar />
        </InfoWrapper>
        <CardWrapper>
          <CarInfo>
            <IconBtn>
              <ArrowLeft fill={theme.color.gray200} />
            </IconBtn>
            <InfoSection>
              <OrderInfo>첫번째 유사견적서</OrderInfo>
              <TrimTitle>Le Blanc</TrimTitle>
              <TypeTagWrapper>
                <TypeTag>디젤 2.2</TypeTag>
                <TypeTag>7인승</TypeTag>
                <TypeTag>2WD</TypeTag>
              </TypeTagWrapper>
              <TotalPrice>41,980,000 원</TotalPrice>
              <Difference>+ 560,000원</Difference>
            </InfoSection>
            <ImgWrapper></ImgWrapper>
          </CarInfo>
          <OptionInfo>
            <HmgTagWrapper>
              <HmgTag size="small" />
            </HmgTagWrapper>
            <OptionSection>
              <p>내 견적에 없는 옵션이에요.</p>
              <OptionCardWrapper>
                <ExtraOptionCard
                  active={false}
                  title="디젤 2.2"
                  price={0}
                  onClick={() => null}
                ></ExtraOptionCard>
                <ExtraOptionCard
                  active={false}
                  title="디젤 2.2"
                  price={0}
                  onClick={() => null}
                ></ExtraOptionCard>
              </OptionCardWrapper>
            </OptionSection>
            <IconBtn>
              <ArrowRight fill={theme.color.gray200} />
            </IconBtn>
          </OptionInfo>
        </CardWrapper>
        <TmpBtn type={'price'}>옵션을 선택해 추가해보세요.</TmpBtn>
      </Modal>
    </DimmedBackground>
  );
}

const Modal = styled.div`
  position: relative;
  display: flex;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  width: 850px;
  height: 520px;
  border-radius: 8px;
  background: ${({ theme }) => theme.color.white};

  padding: 20px 20px 72px 20px;
  display: flex;
  flex-direction: column;
`;

const Header = styled.div`
  display: flex;
  justify-content: flex-end;
`;
const CloseBtn = styled.button``;

const InfoWrapper = styled.div`
  padding: 10px 24px;
  display: flex;
  justify-content: space-between;
`;
const TextWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 367px;
  gap: 15px;
`;
const TitleText = styled.div`
  width: 223px;
  ${HeadingKrMedium6}
`;
const BlueText = styled.span`
  color: ${({ theme }) => theme.color.activeBlue2};
`;

const DescText = styled.div`
  ${BodyKrRegular4}
  color: ${({ theme }) => theme.color.primaryColor};
`;

const CardWrapper = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
  margin-top: 25px;
  border: 1px solid ${({ theme }) => theme.color.skyBlue};
  border-radius: 2px;
`;

const CarInfo = styled.div`
  display: flex;
  border-radius: 2px;
  height: 100%;
  align-items: center;
`;
const OptionInfo = styled.div`
  display: flex;
  position: absolute;
  top: 0;
  right: 0;
  width: 275px;
  height: 100%;
  border-radius: 0px 1px 1px 0px;
  background-color: ${({ theme }) => theme.color.gray50};
  padding: 0 16px;

  p {
    color: ${({ theme }) => theme.color.gray900};
    ${BodyKrMedium5};
    padding-bottom: 4px;
  }
`;

const OptionSection = styled.div`
  margin-top: 60px;
`;
const HmgTagWrapper = styled.div`
  position: absolute;
  top: 29px;
  left: 0;
`;
const OptionCardWrapper = styled.div`
  display: flex;
  gap: 8px;
`;

const IconBtn = styled.button`
  padding: 8px;
`;

const InfoSection = styled.div`
  padding: 0 20px 0 8px;
`;

const OrderInfo = styled.div`
  color: ${({ theme }) => theme.color.gray900};
  ${BodyKrRegular5}
`;

const TrimTitle = styled.p`
  color: ${({ theme }) => theme.color.primaryColor700};
  ${HeadingKrBold6}
`;
const TypeTagWrapper = styled.div`
  display: flex;
  gap: 10px;
  height: 16px;
`;
const TypeTag = styled.div`
  ${flexCenterCss}
  ${BodyKrRegular5}
  padding: 0px 4px;
  background: rgba(117, 117, 117, 0.5);
  color: ${({ theme }) => theme.color.gray50};
`;
const TotalPrice = styled.p`
  color: ${({ theme }) => theme.color.primaryColor700};
  ${BodyKrMedium3}
`;
const Difference = styled.span`
  color: ${({ theme }) => theme.color.sand};
  ${BodyKrRegular5};
`;

const ImgWrapper = styled.div`
  width: 274px;
  height: 156px;
  background-image: url('images/similar_quote/similar1.png');
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
`;

const TmpBtn = styled(RectButton)`
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 52px;
  padding: 14px 0;
  border-radius: 0 0 8px 8px;
  background-color: ${({ theme }) => theme.color.gray300};
`;
